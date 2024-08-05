package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.*;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableThickness;
import ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.IdentifiedDefectService;
import ru.nabokovsg.diagnosedNK.service.norms.AcceptableThicknessService;

@Service
@RequiredArgsConstructor
public class UltrasonicThicknessMeasurementServiceImpl implements UltrasonicThicknessMeasurementService {

    private final UltrasonicThicknessMeasurementRepository repository;
    private final UltrasonicThicknessMeasurementMapper mapper;
    private final AcceptableThicknessService acceptableThicknessService;
    private final IdentifiedDefectService identifiedDefectService;


    @Override
    public UltrasonicThicknessMeasurement save(UltrasonicThicknessMeasurementDto measurementDto
                                             , DiagnosticEquipmentData objectData
                                             , ElementData objectElementData) {
        AcceptableThickness acceptableThickness = acceptableThicknessService.getByPredicate(
                                                                                  objectData.getEquipmentTypeId()
                                                                                , objectElementData.getElementId()
                                                                                , objectElementData.getPartElementId()
                                                                                , measurementDto.getDiameter());
        UltrasonicThicknessMeasurement measurement = mapper.mapToUltrasonicThicknessMeasurement(measurementDto);
        getAcceptableMin(measurement, acceptableThickness, objectElementData);
        setResidualThickness(measurement
                , identifiedDefectService.getMaxCorrosionValueByPredicate(measurementDto, objectData.getEquipmentId()));
        setAcceptableMeasurement(measurement, acceptableThickness);
        return repository.save(measurement);
    }

    @Override
    public UltrasonicThicknessMeasurement update(UltrasonicThicknessMeasurementDto measurementDto
                                               , UltrasonicThicknessMeasurement measurement
                                               , DiagnosticEquipmentData objectData
                                               , ElementData objectElementData) {
        measurement = mapper.mapToUpdateUltrasonicThicknessMeasurement(measurement, measurementDto);
        AcceptableThickness acceptableThickness = acceptableThicknessService.getByPredicate(
                                                                                  objectData.getEquipmentTypeId()
                                                                                , objectElementData.getElementId()
                                                                                , objectElementData.getPartElementId()
                                                                                , measurementDto.getDiameter());
        getAcceptableMin(measurement, acceptableThickness, objectElementData);
        setResidualThickness(measurement
                , identifiedDefectService.getMaxCorrosionValueByPredicate(measurementDto, objectData.getEquipmentId()));
        setAcceptableMeasurement(measurement, acceptableThickness);
        return repository.save(measurement);
    }

    @Override
    public UltrasonicThicknessMeasurement getPredicateData(UTPredicateData predicateData) {
        QUltrasonicThicknessMeasurement measurement = QUltrasonicThicknessMeasurement.ultrasonicThicknessMeasurement;
        QUltrasonicThicknessElementMeasurement element = QUltrasonicThicknessElementMeasurement.ultrasonicThicknessElementMeasurement;
        QUltrasonicThicknessPartElementMeasurement partElement = QUltrasonicThicknessPartElementMeasurement.ultrasonicThicknessPartElementMeasurement;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(element.equipmentId.eq(predicateData.getEquipmentId()));
        builder.and(element.elementId.eq(predicateData.getElementId()));
        if (predicateData.getPartElementId() != null) {
            builder.and(partElement.partElementId.eq(predicateData.getPartElementId()));
        }
        builder.and(measurement.measurementNumber.eq(predicateData.getMeasurementNumber()));
        return null;
    }

    private void setResidualThickness(UltrasonicThicknessMeasurement measurement, Double maxCorrosion) {
        if (maxCorrosion != null) {
            measurement.setResidualThickness(measurement.getMinMeasurementValue() - maxCorrosion);
        } else {
            measurement.setResidualThickness(measurement.getMinMeasurementValue());
        }
    }

    private void getAcceptableMin(UltrasonicThicknessMeasurement measurement
                                , AcceptableThickness acceptableThickness
                                , ElementData objectElementData) {
        if (acceptableThickness.getAcceptablePercent() != null) {
            measurement.setMinAcceptableValue(objectElementData.getDesignThickness()
                    * (acceptableThickness.getAcceptablePercent()/100));
        } else {
            measurement.setMinAcceptableValue(acceptableThickness.getMinThickness());
        }
    }

    private void setAcceptableMeasurement(UltrasonicThicknessMeasurement measurement
                                        , AcceptableThickness acceptableThickness) {
        measurement.setNoStandard(acceptableThickness.getMinThickness() == null);
        measurement.setAcceptable(measurement.getResidualThickness() >= sum(acceptableThickness.getMinThickness()
                                                                          , acceptableThickness.getMeasurementError())
        );
        // Недопустимое значение(брак)
        measurement.setInvalid(sum(measurement.getResidualThickness(), acceptableThickness.getMeasurementError())
                                 < acceptableThickness.getMinThickness());
        // Приближается к недопустимому значению(брак)
        measurement.setApproachingInvalid(measurement.getResidualThickness() > acceptableThickness.getMinThickness()
                && sum(acceptableThickness.getMinThickness(), acceptableThickness.getMeasurementError())
                     > measurement.getResidualThickness()
        );
        measurement.setReachedInvalid(sum(measurement.getResidualThickness(), acceptableThickness.getMeasurementError())
                                       == acceptableThickness.getMinThickness());
        setValidity(measurement);
    }

    private void setValidity(UltrasonicThicknessMeasurement measurement) {
        if (measurement.getNoStandard()) {
            measurement.setValidity("Отсутствуют нормы для сравнения");
        }
        if (measurement.getAcceptable()) {
            measurement.setValidity("Допустимое значение");
        }
        if (measurement.getInvalid()) {
            measurement.setValidity("Ниже предельного допустимого значения");
        }
        if (measurement.getReachedInvalid()) {
            measurement.setValidity("Достигнуто предельное допустимое значение");
        }
    }

    private double sum(double first, double second) {
        return first + second;
    }
}