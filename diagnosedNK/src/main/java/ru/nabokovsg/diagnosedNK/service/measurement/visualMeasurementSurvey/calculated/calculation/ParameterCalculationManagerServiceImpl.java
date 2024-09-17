package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.ParameterCalculationManagerMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
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
public class ParameterCalculationManagerServiceImpl implements ParameterCalculationManagerService {

    private final ParameterCalculationManagerMapper mapper;
    private final CalculateMeasurementVMSService calculateMeasurementService;

    @Override
    public Map<String, CalculatedParameter> calculate(CalculatedParameterData parameterData) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        switch (parameterData.getCalculationType()) {
            case SQUARE -> calculateOneByOne(parameterData, parameters);
            case MIN, MAX, MAX_MIN -> calculateAll(parameterData, parameters);
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        return parameters;
    }

    private void calculateOneByOne(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT ->  calculateDefectsOneByOne(parameterData, parameters);
            case REPAIR ->  calculateRepairOneByOne(parameterData, parameters);
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private void calculateAll(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT -> calculateAllDefects(parameterData, parameters);
            case REPAIR -> calculateAllRepairs(parameterData, parameters);
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private void calculateDefectsOneByOne(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        int measurementNumber = 1;
        for (IdentifiedDefect defect : parameterData.getDefects()) {
            compareParameters(parameters, calculationCalculationType(
                                                        defect.getParameterMeasurements()
                                                                .stream()
                                                                .map(p -> map(p, parameterData.getCalculationType()))
                                                                .collect(Collectors.toSet())
                                                        , parameterData.getCalculationType()));
            setSequentialParameterNumber(parameters, measurementNumber);
            measurementNumber++;
        }
    }

    private void calculateAllDefects(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        Set<CalculatedParameter> calculatedParameters =
                parameterData.getDefects()
                        .stream()
                        .map(IdentifiedDefect::getParameterMeasurements)
                        .flatMap(Collection::stream)
                        .map(p -> map(p, parameterData.getCalculationType()))
                        .collect(Collectors.toSet());
        calculationCalculationType(calculatedParameters, parameterData.getCalculationType());
        addAllParameters(parameters, calculatedParameters);
        setSequentialParameterNumber(parameters, 1);
    }

    private void calculateRepairOneByOne(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        int measurementNumber = 1;
        for (CompletedRepair repair : parameterData.getRepairs()) {
            compareParameters(parameters, calculationCalculationType(
                    repair.getParameterMeasurements()
                            .stream()
                            .map(p -> map(p, parameterData.getCalculationType()))
                            .collect(Collectors.toSet())
                    , parameterData.getCalculationType()));
            setSequentialParameterNumber(parameters, measurementNumber);
            measurementNumber++;
        }
    }

    private void calculateAllRepairs(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        Set<CalculatedParameter> calculatedParameters = parameterData.getRepairs()
                .stream()
                .map(CompletedRepair::getParameterMeasurements)
                .flatMap(Collection::stream)
                .map(p -> map(p, parameterData.getCalculationType()))
                .collect(Collectors.toSet());
        calculationCalculationType(calculatedParameters, parameterData.getCalculationType());
        addAllParameters(parameters, calculatedParameters);
        setSequentialParameterNumber(parameters, 1);
    }

    public Map<String, CalculatedParameter> calculationCalculationType(Set<CalculatedParameter> parameters, ParameterCalculationType calculation) {
        Map<String, CalculatedParameter> calculatedParameters = new HashMap<>();
        switch (calculation) {
            case NO_ACTION -> parameters.forEach(parameter ->
                    calculatedParameters.put(parameter.getParameterName(), parameter)
            );
            case SQUARE -> calculateMeasurementService.countSquare(calculatedParameters, parameters);
            case MIN -> calculateMeasurementService.countMin(calculatedParameters, parameters);
            case MAX -> calculateMeasurementService.countMax(calculatedParameters, parameters);
            case MAX_MIN -> calculateMeasurementService.countMaxMin(calculatedParameters, parameters);
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
        return calculatedParameters;
    }

    private void compareParameters(Map<String, CalculatedParameter> parameters, Map<String, CalculatedParameter> calculatedParameters) {
        if (parameters.isEmpty()) {
            parameters.putAll(calculatedParameters);
            return;
        }
        String quantityName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<Integer, Integer> coincidences = calculationNumberOfMatches(calculatedParameters, parameters);
        parameters.forEach((k, v) -> {
            if (quantityName.equals(v.getParameterName()) && coincidences.get(v.getMeasurementNumber()) == parameters.size()) {
                CalculatedParameter calculatedParameter = calculatedParameters.get(quantityName);
                CalculatedParameter parameter = parameters.get(quantityName);
                calculatedParameter.setIntegerValue(calculatedParameter.getIntegerValue() + parameter.getIntegerValue());
                calculatedParameters.put(quantityName, calculatedParameter);
            }
        });
    }

    private Map<Integer, Integer> calculationNumberOfMatches(Map<String, CalculatedParameter> parameters
            , Map<String, CalculatedParameter> calculatedParameters) {
        Map<Integer, Integer> coincidences = new HashMap<>();
        boolean coincidence = true;
        for (CalculatedParameter parameter : parameters.values()) {
            CalculatedParameter measurement = calculatedParameters.get(parameter.getParameterName());
            if (!parameter.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                coincidence = Objects.equals(parameter.getMinValue(), measurement.getMinValue());
            }
            if (coincidence) {
                coincidences.merge(parameter.getMeasurementNumber(), 1, Integer::sum);
            }
        }
        return coincidences;
    }

    private void setSequentialParameterNumber(Map<String, CalculatedParameter> parameters
            , Integer measurementNumber) {
        int sequentialNumber = 1;
        int size = parameters.size();
        for (CalculatedParameter parameter : parameters.values()) {
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

    private CalculatedParameter map(ParameterMeasurement parameter, ParameterCalculationType calculation) {
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

    private void addAllParameters(Map<String, CalculatedParameter> parameters, Set<CalculatedParameter> calculatedParameters) {
        String parameterName = MeasuredParameterType.valueOf("QUANTITY").label;
        calculatedParameters.forEach(parameter -> {
            if (parameter.getParameterName().equals(parameterName)) {
                addQuantity(parameters, parameter);
            } else {
                parameters.put(parameter.getParameterName(), parameter);
            }
        });
    }

    private void addQuantity(Map<String, CalculatedParameter> parameters, CalculatedParameter parameter) {
        if (!parameters.isEmpty()) {
            CalculatedParameter quantity = parameters.get(parameter.getParameterName());
            parameter.setIntegerValue(parameter.getIntegerValue() + quantity.getIntegerValue());
        }
        if (parameter.getIntegerValue() > 1) {
            parameters.put(parameter.getParameterName(), parameter);
        }
    }
}