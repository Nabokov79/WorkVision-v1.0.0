package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurementFactoryMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
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
        Map<String, Set<CalculateParameterMeasurement>> calculateParameters = new HashMap<>();
        parameterMeasurements.forEach((k,v) -> {
            if (calculationType == ParameterCalculationType.SQUARE) {
                createSquareParameter(v, calculateParameters);
            } else {
                throw new NotFoundException(String.format("Calculation type=%s not supported", calculationType));
            }
        });
        return calculateParameters.values().stream().toList();
    }

    private void createSquareParameter(Set<CalculateParameterMeasurement> parameterMeasurements, Map<String, CalculateParameterMeasurement> calculateParameters) {
        Map<String, CalculateParameterMeasurement> parameters = parameterMeasurements.stream().collect(Collectors.toMap(CalculateParameterMeasurement::getParameterName, p -> p));
        String parameterName = MeasuredParameterType.valueOf("SQUARE").label;
        String unitMeasurement = UnitMeasurementType.valueOf("M_2").label;
        double parameterValue = methodsCalculate.countSquare(parameterMeasurements);
        CalculateParameterMeasurement square = mapper.mapToCalculateParameterMeasurement(parameterName, unitMeasurement, parameterValue);
        compareParameters(calculateParameters, square);
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

    private void compareParameters(Map<String, CalculateParameterMeasurement> calculateParameters, CalculateParameterMeasurement parameter, CalculateParameterMeasurement quantity, Long id) {
        if (calculateParameters.isEmpty()) {
            calculateParameters.put(parameter.getParameterName(), parameter);
            return;
        }
        String quantityName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<Integer, Integer> coincidences = calculationNumberOfMatches(calculatedParameters, parameters);
        calculateParameters.forEach((k, v) -> {
            if (quantityName.equals(parameter.getParameterName()) && parameter.getMinValue().equals(v.getMinValue())) {

            }
        });
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