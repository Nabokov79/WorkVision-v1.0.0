package ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.*;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableThickness;
import ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.QueryDSLRequestService;
import ru.nabokovsg.diagnosedNK.service.norms.AcceptableThicknessService;

@Service
@RequiredArgsConstructor
public class UltrasonicThicknessMeasurementServiceImpl implements UltrasonicThicknessMeasurementService {

    private final UltrasonicThicknessMeasurementRepository repository;
    private final UltrasonicThicknessMeasurementMapper mapper;
    private final AcceptableThicknessService acceptableThicknessService;
    private final QueryDSLRequestService requestService;

    @Override
    public UltrasonicThicknessMeasurement save(UltrasonicThicknessMeasurementDto measurementDto, StandardSize standardSize) {
        UltrasonicThicknessMeasurement measurement = mapper.mapToUltrasonicThicknessMeasurement(measurementDto);
        set(measurement, measurementDto, standardSize);
        return repository.save(measurement);
    }

    @Override
    public UltrasonicThicknessMeasurement update(UltrasonicThicknessMeasurementDto measurementDto
                                               , UltrasonicThicknessMeasurement measurement
                                               , StandardSize standardSize) {
        measurement = mapper.mapToUpdateUltrasonicThicknessMeasurement(measurement, measurementDto);
        set(measurement, measurementDto, standardSize);
        return repository.save(measurement);
    }

    private void set(UltrasonicThicknessMeasurement measurement
                   , UltrasonicThicknessMeasurementDto measurementDto
                   , StandardSize standardSize) {
        AcceptableThickness acceptableThickness = acceptableThicknessService.getByPredicate(
                                                      requestService.getEquipmentTypeId(measurementDto.getElementId())
                                                    , measurementDto.getElementId()
                                                    , measurementDto.getPartElementId()
                                                    , measurementDto.getDiameter());
        getAcceptableMin(measurement, acceptableThickness, standardSize);
        setResidualThickness(measurement, measurementDto);
        setAcceptableMeasurement(measurement, acceptableThickness);
    }

    private void setResidualThickness(UltrasonicThicknessMeasurement measurement
                                    , UltrasonicThicknessMeasurementDto measurementDto) {
        Double maxCorrosion = requestService.getMaxCorrosionValueByPredicate(measurementDto
                                                                                    , measurementDto.getEquipmentId());
        if (maxCorrosion != null) {
            measurement.setResidualThickness(measurement.getMinMeasurementValue() - maxCorrosion);
        } else {
            measurement.setResidualThickness(measurement.getMinMeasurementValue());
        }
    }

    private void getAcceptableMin(UltrasonicThicknessMeasurement measurement
                                , AcceptableThickness acceptableThickness
                                , StandardSize standardSize) {
        if (acceptableThickness.getAcceptablePercent() != null) {
            measurement.setMinAcceptableValue(standardSize.getDesignThickness()
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