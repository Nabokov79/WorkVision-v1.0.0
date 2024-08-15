package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.CalculationDefectOrRepair;
import ru.nabokovsg.diagnosedNK.model.norms.CalculationParameter;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;
import ru.nabokovsg.diagnosedNK.service.measurement.CalculationMeasurementService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculationVMMeasurementServiceImpl implements CalculationVMMeasurementService {

    private final CalculationMeasurementService calculationService;

    @Override
    public Set<ParameterMeasurement> calculation(CalculationDefectOrRepair calculation
            , Set<MeasuredParameter> measuredParameters
            , Set<ParameterMeasurement> parameterMeasurements
            , List<ParameterMeasurementDto> parameterMeasurementsDto) {
        Map<Long, CalculationParameter> typeCalculationsDb = measuredParameters
                .stream()
                .collect(Collectors.toMap(MeasuredParameter::getId, MeasuredParameter::getCalculation));
        Map<Long, ParameterMeasurement> parameterMeasurementsDb = parameterMeasurements.stream()
                .collect(Collectors.toMap(ParameterMeasurement::getId, r -> r));
        switch (calculation) {
            case NO_ACTION -> { return parameterMeasurementsDto.stream()
                    .map(p -> calculationParameter(typeCalculationsDb.get(p.getParameterId())
                            , parameterMeasurementsDb.get(p.getParameterId())
                            , p))
                    .collect(Collectors.toSet());
            }
            case SQUARE -> {
                return calculationService.countSquare(
                        parameterMeasurements.stream()
                                .collect(Collectors.toMap(ParameterMeasurement::getId, p -> p))
                        , parameterMeasurementsDto.stream()
                                .collect(Collectors.toMap(p -> parameterMeasurementsDb.get(p.getParameterId())
                                                .getParameterName()
                                        , p -> p)));
            }
            case QUANTITY -> {
                return calculationService.countQuantity(
                        parameterMeasurements.stream()
                                .collect(Collectors.toMap(ParameterMeasurement::getId, p -> p))
                        , parameterMeasurementsDto.stream()
                                .collect(Collectors.toMap(p -> parameterMeasurementsDb.get(p.getParameterId())
                                                .getParameterName()
                                        , p -> p)));}
            default -> {return parameterMeasurements;}
        }
    }

    private ParameterMeasurement calculationParameter(CalculationParameter calculation
            , ParameterMeasurement parameterMeasurement
            , ParameterMeasurementDto parameterMeasurementDto) {
        switch (calculation) {
            case MIN -> {
                return calculationService.countMin(parameterMeasurement, parameterMeasurementDto);
            }
            case MAX -> {
                return calculationService.countMax(parameterMeasurement, parameterMeasurementDto);
            }
            case MAX_MIN -> {
                return calculationService.countMaxAndMin(parameterMeasurement, parameterMeasurementDto);
            }
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
    }
}