package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

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
    private final static String QUANTITY = MeasuredParameterType.valueOf("QUANTITY").label;

    @Override
    public List<CalculateParameterMeasurement> calculateOneByOne(Map<Long, Set<CalculateParameterMeasurement>> parameterMeasurements, ParameterCalculationType calculationType) {
        Map<Integer, Set<CalculateParameterMeasurement>> calculateParameters = new HashMap<>();
        int measurementNumber = 1;
        parameterMeasurements.forEach((k, v) -> {
            CalculateParameterMeasurement calculateParameter = getCalculateParameter(v, calculationType);
            addParameters(Map<Integer, Set<CalculateParameterMeasurement>> calculateParameters, Set<CalculateParameterMeasurement> parameters, calculationType, measurementNumber);
            if (calculateParameters.isEmpty()) {
                setSequentialParameterNumber(parameters, measurementNumber);
                return parameters;
            } else {
                compare(calculateParameters, parameters).forEach((k, v) -> {
                    if (v) {
                        countQuantity(calculateParameters.get(k), newParameters.get(QUANTITY));
                    }
                });
            }
            addParameters(calculateParameters, Set.of(calculateParameter));
        });
        return calculateParameters.values().stream().flatMap(Collection::stream).toList();
    }

    @Override
    public List<CalculateParameterMeasurement> calculateAll(Map<Long, Set<CalculateParameterMeasurement>> parameterMeasurements, ParameterCalculationType calculationType) {
        Map<Integer, Set<CalculateParameterMeasurement>> calculateParameters = new HashMap<>();
        int measurementNumber = 1;
        parameterMeasurements.forEach((k, v) -> {
            switch (calculationType) {
                case MIN -> methodsCalculate.countMin(calculateParameters, v);
                case MAX -> methodsCalculate.countMax(calculateParameters, v);
                case MAX_MIN -> methodsCalculate.countMaxMin(calculateParameters, v);
                default ->
                        throw new NotFoundException(String.format("Calculation type=%s not supported", calculationType));
            }
        });
        return calculateParameters.values().stream().toList();
    }

    private void addParameters(Map<Integer, Set<CalculateParameterMeasurement>> calculateParameters, Set<CalculateParameterMeasurement> parameters, ParameterCalculationType calculationType, int measurementNumber) {
        List<CalculateParameterMeasurement> filterParameters = filterParameters(parameters, calculationType);
        addQuantity(v, parameters);
        if (calculateParameters.isEmpty()) {
            setSequentialParameterNumber(filterParameters, measurementNumber);
            calculateParameters.put(measurementNumber, parameters);
        } else {
            Map<String, CalculateParameterMeasurement> newParameters = parameters.stream().collect(Collectors.toMap(CalculateParameterMeasurement::getParameterName, parameter -> parameter));
            compare(calculateParameters, parameters).forEach((k, v) -> {
                if (v) {
                    countQuantity(calculateParameters.get(k), newParameters.get(QUANTITY));
                }
            });
        }
    }

    private List<CalculateParameterMeasurement> filterParameters(Set<CalculateParameterMeasurement> parameterMeasurements, ParameterCalculationType calculationType) {
        if (calculationType.equals(ParameterCalculationType.AREA)) {
            String length = MeasuredParameterType.valueOf("LENGTH").label;
            String width = MeasuredParameterType.valueOf("WIDTH").label;
            String diameter = MeasuredParameterType.valueOf("DIAMETER").label;
            return parameterMeasurements.stream()
                    .filter(v -> !v.getParameterName().equals(length) && !v.getParameterName().equals(width) && !v.getParameterName().equals(diameter))
                    .toList();
        }
        throw new NotFoundException(String.format("Filter by calculation type=%s not supported", calculationType));
    }

    private void setSequentialParameterNumber(List<CalculateParameterMeasurement> parameters, Integer measurementNumber) {
        int sequentialNumber = 1;
        int size = parameters.size();
        for (CalculateParameterMeasurement parameter : parameters) {
            if (parameter.getMeasurementNumber() == null) {
                if (parameter.getParameterName().equals(MeasuredParameterType.valueOf("SQUARE").label)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                } else if (parameter.getParameterName().equals(QUANTITY)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, size);
                } else{
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                }
            }
        }
    }






    private CalculateParameterMeasurement getCalculateParameter(Set<CalculateParameterMeasurement> parameterMeasurements, ParameterCalculationType calculationType) {
        if (calculationType.equals(ParameterCalculationType.AREA)) {
            return mapper.mapToCalculateParameterMeasurement(MeasuredParameterType.valueOf("AREA").label
                                                           , UnitMeasurementType.valueOf("M_2").label
                                                           , methodsCalculate.countArea(parameterMeasurements));
        } else {
            throw new NotFoundException(String.format("Calculation type=%s not supported", calculationType));
        }
    }





    private void addQuantity(Set<CalculateParameterMeasurement> parameterMeasurements, List<CalculateParameterMeasurement> calculateParameters) {
        parameterMeasurements.forEach(parameterMeasurement -> {
            if (parameterMeasurement.getParameterName().equals(QUANTITY) && parameterMeasurement.getIntegerValue() > 1) {
                calculateParameters.add(parameterMeasurement);
            }
        });
    }

    private Map<Integer, Boolean> compare(Map<Integer, Set<CalculateParameterMeasurement>> calculateParameters, List<CalculateParameterMeasurement> parameters) {
        Map<Integer, Boolean> equals = new HashMap<>(1);
        calculateParameters.forEach((k, v) ->
                equals.put(k, Objects.equals(v.stream().filter(p -> !p.getParameterName().equals(QUANTITY)).toList()
                        , parameters.stream().filter(p -> !p.getParameterName().equals(QUANTITY)).toList()))
        );
        return equals;
    }



    private Map<Integer, Integer> calculationNumberOfMatches(Map<String, CalculateParameterMeasurement> parameters
            , Map<String, CalculateParameterMeasurement> calculatedParameters) {
        Map<Integer, Integer> coincidences = new HashMap<>();
        boolean coincidence = true;
        for (CalculateParameterMeasurement parameter : parameters.values()) {
            CalculateParameterMeasurement measurement = calculatedParameters.get(parameter.getParameterName());
            if (!parameter.getParameterName().equals(QUANTITY)) {
                coincidence = Objects.equals(parameter.getMinValue(), measurement.getMinValue());
            }
            if (coincidence) {
                coincidences.merge(parameter.getMeasurementNumber(), 1, Integer::sum);
            }
        }
        return coincidences;
    }


    private void countQuantity(Set<CalculateParameterMeasurement> calculatedParameters, CalculateParameterMeasurement quantity) {
        calculatedParameters.forEach(v -> {
            if (v.getParameterName().equals(QUANTITY)) {
                v.setIntegerValue(v.getIntegerValue() + quantity.getIntegerValue());
            }
        });
    }
}