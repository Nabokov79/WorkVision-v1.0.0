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

    @Override
    public List<CalculateParameterMeasurement> calculate(CalculatedParameterData parameterData) {
        Map<String, CalculateParameterMeasurement> parameters = new HashMap<>();
        switch (parameterData.getCalculationType()) {
            case SQUARE -> calculateOneByOne(parameterData, parameters);
            case MIN, MAX, MAX_MIN -> calculateAll(parameterData, parameters);
            case NO_ACTION -> getAllParameters(parameterData);
            default -> throw new NotFoundException(String.format("Calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        return new ArrayList<>(parameters.values());
    }

    public void calculateOneByOne(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        int measurementNumber = 1;
        switch (parameterData.getTypeData()) {
            case DEFECT -> {
                for (IdentifiedDefect defect : parameterData.getDefects()) {
                    measurementNumber = calculateParametersOneByOne(defect.getParameterMeasurements(), parameters,  parameterData.getCalculationType(), measurementNumber);
                }
            }
            case REPAIR -> {
                for (CompletedRepair repair : parameterData.getRepairs()) {
                    measurementNumber = calculateParametersOneByOne(repair.getParameterMeasurements(), parameters,  parameterData.getCalculationType(), measurementNumber);
                }
            }
            default -> throw new NotFoundException(String.format("Type data=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    public int calculateParametersOneByOne(Set<ParameterMeasurement> parameterMeasurements, Map<String, CalculateParameterMeasurement> parameters, ParameterCalculationType calculationType, int measurementNumber) {
        compareParameters(parameters, methodsCalculate.calculationByCalculationType(
                                                              parameterMeasurements.stream()
                                                                                   .map(p -> mapTo(p, calculationType))
                                                                                   .collect(Collectors.toSet())
                                                            , calculationType));
        setSequentialParameterNumber(parameters, measurementNumber);
        return ++measurementNumber;
    }

    public void calculateAll(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        Set<CalculateParameterMeasurement> calculatedParameters = getAllParameters(parameterData);
        methodsCalculate.calculationByCalculationType(calculatedParameters, parameterData.getCalculationType());
        countQuantity(parameters, calculatedParameters);
        setSequentialParameterNumber(parameters, 1);
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
            default -> throw new NotFoundException(String.format("Type=%s not supported"
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

    private void mapWith(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT -> parameters.forEach((k,v) -> parameters.put(k, mapper.mapWithDefect(v, parameterData.getDefect())));
            case REPAIR -> parameters.forEach((k,v) -> parameters.put(k, mapper.mapWithRepair(v, parameterData.getRepair())));
            default -> throw new NotFoundException(String.format("Type data=%s not supported"
                    , parameterData.getCalculationType()));
        }
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