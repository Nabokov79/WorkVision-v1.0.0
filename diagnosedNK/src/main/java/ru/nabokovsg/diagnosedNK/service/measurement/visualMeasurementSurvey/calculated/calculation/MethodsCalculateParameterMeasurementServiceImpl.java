package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculateMeasurementVMSMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MethodsCalculateParameterMeasurementServiceImpl implements MethodsCalculateParameterMeasurementService {
    private final CalculateMeasurementVMSMapper mapper;

    @Override
    public Map<String, CalculateParameterMeasurement> calculationByCalculationType(Set<CalculateParameterMeasurement> measurements, ParameterCalculationType calculationType) {
        Map<String, CalculateParameterMeasurement> calculatedParameters = new HashMap<>();
        switch (calculationType) {
            case SQUARE -> countSquare(calculatedParameters, measurements);
            case MIN -> countMin(calculatedParameters, measurements);
            case MAX -> countMax(calculatedParameters, measurements);
            case MAX_MIN -> countMaxMin(calculatedParameters, measurements);
            default -> measurements.forEach(parameter ->
                    calculatedParameters.put(parameter.getParameterName(), map(parameter, calculationType))
            );
        }
        return calculatedParameters;
    }

    @Override
    public void countQuantity(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        String parameterName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<String, CalculateParameterMeasurement> calculatedParameter = new HashMap<>(1);
        measurements.forEach(v -> {
            if (v.getParameterName().equals(parameterName)) {
                CalculateParameterMeasurement parameter = calculatedParameter.get(parameterName);
                if (parameter == null) {
                    calculatedParameter.put(parameterName, mapper.mapToQuantity(v));
                } else {
                    parameter.setIntegerValue(parameter.getIntegerValue() + v.getIntegerValue());
                    calculatedParameters.put(parameterName, parameter);
                }
            }
        });
        calculatedParameter.forEach((k,v) -> {
            if (v.getIntegerValue() > 1) {
                calculatedParameters.put(k, v);
            }
        });
    }

    private void countMin(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculateParameterMeasurement parameter = calculatedParameters.get(p.getParameterName());
                if (parameter == null) {
                    calculatedParameters.put(p.getParameterName(), map(p, ParameterCalculationType.MIN));
                } else {
                    parameter.setMinValue(Math.min(p.getValue(), parameter.getMinValue()));
                }
            }
        });
    }

   private void countMax(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculateParameterMeasurement parameter = calculatedParameters.get(p.getParameterName());
                if (parameter == null) {
                    calculatedParameters.put(p.getParameterName(), map(p, ParameterCalculationType.MAX));
                } else {
                    parameter.setMaxValue(Math.max(p.getValue(), parameter.getMaxValue()));
                }
            }
        });
    }

    private void countMaxMin(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculateParameterMeasurement parameter = calculatedParameters.get(p.getParameterName());
                if (parameter == null) {
                    calculatedParameters.put(p.getParameterName(), map(p, ParameterCalculationType.MAX_MIN));
                } else {
                    parameter.setMinValue(Math.min(p.getValue(), parameter.getMinValue()));
                    parameter.setMaxValue(Math.max(p.getValue(), parameter.getMaxValue()));
                    if (Objects.equals(parameter.getMinValue(), parameter.getMaxValue())) {
                        parameter.setMinValue(null);
                    }
                }
            }
        });
    }

    private void countSquare(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        Map<String, CalculateParameterMeasurement> parameters = measurements.stream()
                .collect(Collectors.toMap(CalculateParameterMeasurement::getParameterName, p -> p));
        String length = MeasuredParameterType.valueOf("LENGTH").label;
        String width = MeasuredParameterType.valueOf("WIDTH").label;
        String diameter = MeasuredParameterType.valueOf("DIAMETER").label;
        double calculatedSquare = 0.0;
        if (parameters.get(length) != null) {
            calculatedSquare = parameters.get(length).getMinValue() * parameters.get(width).getMinValue();
        }
        if (parameters.get(length) == null && parameters.get(diameter) != null) {
            double rad = parameters.get(diameter).getMinValue() / 2;
            calculatedSquare = Math.PI * rad * rad * 100 / 100;
        }
        calculatedSquare /= 1000000;
        calculatedParameters.put(MeasuredParameterType.valueOf("SQUARE").label
                               , mapper.mapToSquare(MeasuredParameterType.valueOf("SQUARE").label
                                                  , UnitMeasurementType.valueOf("M_2").label
                                                  , calculatedSquare));
    }

    private CalculateParameterMeasurement map(ParameterMeasurement parameter, ParameterCalculationType calculation) {
        if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
            return mapper.mapToQuantity(parameter);
        }
        switch (calculation) {
            case NO_ACTION, MIN, SQUARE -> {
                return mapper.mapToMinValue(parameter);
            }
            case MAX -> {
                return mapper.mapToMaxValue(parameter);
            }
            case MAX_MIN -> {
                return mapper.mapToMaxMinValue(parameter);
            }
            default ->
                    throw new BadRequestException(String.format("Mapping for calculationType=%s is not supported", calculation));
        }
    }
}