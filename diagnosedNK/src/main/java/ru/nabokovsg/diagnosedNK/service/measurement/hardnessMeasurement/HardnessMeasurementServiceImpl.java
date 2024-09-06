package ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.hardnessMeasurement.HardnessMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.HardnessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UTPredicateData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableHardness;
import ru.nabokovsg.diagnosedNK.repository.measurement.hardnessMeasurement.HardnessMeasurementRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.common.QueryDSLRequestService;
import ru.nabokovsg.diagnosedNK.service.norms.AcceptableHardnessService;

@Service
@RequiredArgsConstructor
public class HardnessMeasurementServiceImpl implements HardnessMeasurementService {

    private final HardnessMeasurementRepository repository;
    private final HardnessMeasurementMapper mapper;
    private final AcceptableHardnessService acceptableHardnessService;
    private final QueryDSLRequestService requestService;

    @Override
    public HardnessMeasurement save(HardnessMeasurementDto measurementDto, StandardSize standardSize) {
        AcceptableHardness acceptableHardness =
                acceptableHardnessService.getByPredicate(
                                                  requestService.getEquipmentTypeId(measurementDto.getElementId())
                                                , measurementDto.getElementId()
                                                , measurementDto.getPartElementId()
                                                , standardSize);
        validateHardnessMeasurement(mapper.mapToUTPredicateData(measurementDto)
                                  , acceptableHardness
                                  , standardSize.getMinDiameter());
        return repository.save(setValidityValue(mapper.mapToHardnessMeasurement(measurementDto), acceptableHardness));
    }

    @Override
    public HardnessMeasurement update(HardnessMeasurementDto measurementDto
                                    , HardnessMeasurement measurement
                                    , StandardSize standardSize) {
        AcceptableHardness acceptableHardness =
                acceptableHardnessService.getByPredicate(
                        requestService.getEquipmentTypeId(measurementDto.getElementId())
                        , measurementDto.getElementId()
                        , measurementDto.getPartElementId()
                        , standardSize);
        return repository.save(setValidityValue(mapper.mapToUpdateHardnessMeasurement(measurement, measurementDto)
                                              , acceptableHardness));
    }

    private HardnessMeasurement setValidityValue(HardnessMeasurement measurement, AcceptableHardness acceptableHardness) {
        boolean validityValue = false;
        String validity = "Допустимое значение";
        if (measurement.getMeasurementValue() < acceptableHardness.getMinHardness()) {
            validityValue = true;
            validity = "Меньше предельного допустимого значения";
        }
        if (measurement.getMeasurementValue() > acceptableHardness.getMaxHardness()) {
            validityValue = true;
            validity = "Больше предельного допустимого значения";
        }
        return mapper.mapHardnessMeasurementWithAcceptableValue(measurement, validityValue, validity);
    }
    private void validateHardnessMeasurement(UTPredicateData predicateData
                                           , AcceptableHardness acceptableHardness
                                           , Integer minDiameter) {
        UltrasonicThicknessMeasurement measurement = requestService.getUltrasonicThicknessMeasurement(predicateData);
        boolean flag = measurement.getMinMeasurementValue() < acceptableHardness.getMinAllowableThickness();
        if (flag && minDiameter != null) {
            flag = minDiameter < acceptableHardness.getMinAllowableDiameter();
        }
        if (flag) {
            throw new BadRequestException("The element is not subject to metal hardness measurement");
        }
    }
}