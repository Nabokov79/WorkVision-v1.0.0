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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateParameterMeasurementFactoryImpl implements CalculateParameterMeasurementFactory {

    private final CalculateParameterMeasurementFactoryMapper mapper;
    private final CalculateOneByOneParametersService calculateOneByOneParametersService;
    private final CalculateAllParametersService calculateAllParametersService;
    private final MethodsCalculateParameterMeasurementService methodsCalculate;

    private List<CalculateParameterMeasurement> calculateByCalculationType(CalculatedParameterData parameterData) {
        int measurementNumber = 1;
        Map<String, CalculateParameterMeasurement> parameters = new HashMap<>();
        switch (parameterData.getCalculationType()) {
            case SQUARE -> calculateParametersOneByOne(parameterData, parameters);
            case MIN, MAX, MAX_MIN -> calculateAllParameters(parameterData, parameters);
            case NO_ACTION -> getAllParameters(parameterData);
            default -> throw new NotFoundException(String.format("Calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        return new ArrayList<>(parameters.values());
    }

    public void calculateParametersOneByOne(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT ->
                calculateOneByOneParametersService.calculateOneByOneParameters(parameterData.getDefects(), parameterData.getCalculationType(), parameters);
            case REPAIR ->
                calculateOneByOneParametersService.calculateCompletedRepairOneByOneParameters(parameterData.getRepairs(), parameterData.getCalculationType(), parameters);
            default -> throw new NotFoundException(String.format("Type data=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    public void calculateAllParameters(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        calculateAllParametersService.calculateAllParameters(getAllParameters(parameterData), parameterData.getCalculationType(), parameters);
    }

    private Set<CalculateParameterMeasurement> getAllParameters(CalculatedParameterData parameterData) {
        switch (parameterData.getTypeData()) {
            case DEFECT -> {
                return parameterData.getDefects()
                        .stream()
                        .map(IdentifiedDefect::getParameterMeasurements)
                        .flatMap(Collection::stream)
                        .map(parameter -> mapTo(parameter, parameterData.getCalculationType()))
                        .collect(Collectors.toSet());
            }
            case REPAIR -> {
                return parameterData.getRepairs()
                        .stream()
                        .map(CompletedRepair::getParameterMeasurements)
                        .flatMap(Collection::stream)
                        .map(parameter -> mapTo(parameter, parameterData.getCalculationType()))
                        .collect(Collectors.toSet());
            }
            default -> throw new NotFoundException(String.format("Type data=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private CalculateParameterMeasurement mapTo(ParameterMeasurement parameter, ParameterCalculationType calculation) {
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

    private void mapWith(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT -> parameters.forEach((k,v) -> parameters.put(k, mapper.mapWithDefect(v, parameterData.getDefect())));
            case REPAIR -> parameters.forEach((k,v) -> parameters.put(k, mapper.mapWithRepair(v, parameterData.getRepair())));
            default -> throw new NotFoundException(String.format("Type data=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private void compareParameters(Map<String, CalculateParameterMeasurement> parameters, Map<String, CalculateParameterMeasurement> calculatedParameters) {
        if (parameters.isEmpty()) {
            parameters.putAll(calculatedParameters);
            return;
        }
        String quantityName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<Integer, Integer> coincidences = calculationNumberOfMatches(calculatedParameters, parameters);
        parameters.forEach((k, v) -> {
            if (quantityName.equals(v.getParameterName()) && coincidences.get(v.getMeasurementNumber()) == parameters.size()) {
                CalculateParameterMeasurement calculatedParameter = calculatedParameters.get(quantityName);
                CalculateParameterMeasurement parameter = parameters.get(quantityName);
                calculatedParameter.setIntegerValue(calculatedParameter.getIntegerValue() + parameter.getIntegerValue());
                calculatedParameters.put(quantityName, calculatedParameter);
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