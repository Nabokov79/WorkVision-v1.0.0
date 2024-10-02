package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurementFactoryMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateParameterMeasurementFactoryImpl implements CalculateParameterMeasurementFactory {

    private final CalculateParameterMeasurementFactoryMapper mapper;
    private final MethodsCalculateParameterMeasurementService methodsCalculate;

    @Override
    public List<CalculateParameterMeasurement> calculateOneByOne(Map<Long, Set<CalculateParameterMeasurement>> parameterMeasurements, ParameterCalculationType calculationType) {
        Map<Long, Set<CalculateParameterMeasurement>> calculateParameters = new HashMap<>();
        if (calculationType == ParameterCalculationType.SQUARE) {
            createSquareParameter(parameterMeasurements, calculateParameters);
        } else {
            throw new NotFoundException(String.format("Calculation type=%s not supported", calculationType));
        }
        return calculateParameters.values().stream().flatMap(Collection::stream).toList();
    }

    private void createSquareParameter(Map<Long, Set<CalculateParameterMeasurement>> parameterMeasurements, Map<Long, Set<CalculateParameterMeasurement>> calculateParameters) {
        String parameterName = MeasuredParameterType.valueOf("SQUARE").label;
        String unitMeasurement = UnitMeasurementType.valueOf("M_2").label;
        parameterMeasurements.forEach((k,v) -> {
            CalculateParameterMeasurement square = mapper.mapToCalculateParameterMeasurement(parameterName, unitMeasurement, methodsCalculate.countSquare(v));
            if (!calculateParameters.isEmpty()) {
                calculateParameters.forEach((key,values) ->
                              calculateParameters.put(key, compareByMinValue(values, square, getQuantityParameter(v))));
            } else {
                calculateParameters.put(k, Set.of(square));
            }
        });
    }

    private CalculateParameterMeasurement getQuantityParameter(Set<CalculateParameterMeasurement> calculateParameters) {
        String parameterName = MeasuredParameterType.valueOf("QUANTITY").label;
        for (CalculateParameterMeasurement parameter : calculateParameters) {
            if (parameterName.equals(parameter.getParameterName())) {
                return parameter;
            }
        }
        throw new NotFoundException(String.format("Parameter measurement =%s not found", parameterName));
    }

    private Set<CalculateParameterMeasurement> compareByMinValue(Set<CalculateParameterMeasurement> calculateParameters, CalculateParameterMeasurement parameter, CalculateParameterMeasurement quantity) {
        String parameterName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<String, CalculateParameterMeasurement> parameters = calculateParameters.stream().collect(Collectors.toMap(CalculateParameterMeasurement::getParameterName, c -> c));
        calculateParameters.forEach(v -> {
             if (v.getMinValue().equals(parameter.getMinValue())) {
                 CalculateParameterMeasurement calculateQuantity = parameters.get(parameterName);
                 calculateQuantity.setIntegerValue(calculateQuantity.getIntegerValue() + quantity.getIntegerValue());
                 parameters.put(parameterName, calculateQuantity);

            }
        });
        return new HashSet<>(parameters.values());
    }

    @Override
    public List<CalculateParameterMeasurement> calculateAll(Map<Long, Set<CalculateParameterMeasurement>> parameterMeasurements, ParameterCalculationType calculationType) {
        Map<String, CalculateParameterMeasurement> calculateParameters = new HashMap<>();
        parameterMeasurements.forEach((k,v) -> {
            switch (calculationType) {
                case MIN -> methodsCalculate.countMin(calculateParameters, v);
                case MAX -> methodsCalculate.countMax(calculateParameters, v);
                case MAX_MIN -> methodsCalculate.countMaxMin(calculateParameters, v);
                default -> throw new NotFoundException(String.format("Calculation type=%s not supported", calculationType));
            }
        });
        return calculateParameters.values().stream().toList();
    }

    private Map<Integer, Integer> calculationNumberOfMatches(Map<String, CalculateParameterMeasurement> parameters
            , Map<String, CalculateParameterMeasurement> calculatedParameters) {
        Map<Integer, Integer> coincidences = new HashMap<>();
        boolean coincidence = true;
        for (CalculateParameterMeasurement parameter : parameters.values()) {
            CalculateParameterMeasurement measurement = calculatedParameters.get(parameter.getParameterName());
            if (!parameter.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                coincidence = Objects.equals(parameter.getMinValue(), measurement.getMinValue());
            }
            if (coincidence) {
                coincidences.merge(parameter.getMeasurementNumber(), 1, Integer::sum);
            }
        }
        return coincidences;
    }



    private void setSequentialParameterNumber(Map<String, CalculateParameterMeasurement> parameters
            , Integer measurementNumber) {
        int sequentialNumber = 1;
        int size = parameters.size();
        for (CalculateParameterMeasurement parameter : parameters.values()) {
            if (parameter.getMeasurementNumber() == null) {
                if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("SQUARE").label)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                } else if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, size);
                } else {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                }
            }
        }
    }

    private void countQuantity(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        String parameterName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<String, CalculateParameterMeasurement> calculatedParameter = new HashMap<>(1);
        measurements.forEach(v -> {
            if (v.getParameterName().equals(parameterName)) {
                CalculateParameterMeasurement parameter = calculatedParameter.get(parameterName);
                if (parameter == null) {
                    calculatedParameter.put(parameterName, v);
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
}