package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ParameterCalculationManagerServiceImpl implements ParameterCalculationManagerService {

    private final CalculateMeasurementVMSService calculateMeasurementService;
    private final ParameterCalculationManagerMapper mapper;

    @Override
    public Map<String, CalculatedParameter> calculate(CalculatedParameterData parameterData) {
        log.info(" ");
        log.info(" ----------------       Class : ParameterCalculationManagerServiceImpl -----------------------");
        log.info(" ----------------       START calculate -----------------------");
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        switch (parameterData.getCalculationType()) {
            case SQUARE -> calculateOneByOne(parameterData, parameters);
            case MIN, MAX, MAX_MIN -> calculateAll(parameterData, parameters);
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        log.info(" ");
        log.info("OUTPUT parameters = {}", parameters);
        log.info(" ----------------       END calculate -----------------------");
        return parameters;
    }

    private void calculateOneByOne(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        log.info(" ");
        log.info(" ----------------       START calculateOneByOne -----------------------");
        int measurementNumber = 1;
        switch (parameterData.getTypeData()) {
            case DEFECT -> {
                for (IdentifiedDefect defect : parameterData.getDefects()) {
                    compareParameters(parameters, calculateMeasurementService.calculation(defect.getParameterMeasurements().stream().map(p -> map(p, parameterData.getCalculationType())).collect(Collectors.toSet()), parameterData.getCalculationType()));
                    setSequentialParameterNumber(parameters, measurementNumber);
                    measurementNumber++;
                }
            }
            case REPAIR -> {
                for (CompletedRepair repair : parameterData.getRepairs()) {
                    compareParameters(parameters, calculateMeasurementService.calculation(repair.getParameterMeasurements().stream().map(p -> map(p, parameterData.getCalculationType())).collect(Collectors.toSet()), parameterData.getCalculationType()));
                    setSequentialParameterNumber(parameters, measurementNumber);
                    measurementNumber++;
                }
            }
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        log.info(" ----------------  END calculateOneByOne -----------------------");
    }

    private void calculateAll(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        log.info(" ");
        log.info(" ----------------       START calculate ALL -----------------------");
        switch (parameterData.getTypeData()) {
            case DEFECT ->
                    parameters.putAll(calculateMeasurementService.calculation(
                                                     parameterData.getDefects()
                                                                  .stream()
                                                                  .map(IdentifiedDefect::getParameterMeasurements)
                                                                  .flatMap(Collection::stream)
                                                                  .map(p -> map(p, parameterData.getCalculationType()))
                                                                  .collect(Collectors.toSet())
                                                   , parameterData.getCalculationType()));
            case REPAIR ->
                    parameters.putAll(calculateMeasurementService.calculation(
                                                    parameterData.getRepairs()
                                                                 .stream()
                                                                 .map(CompletedRepair::getParameterMeasurements)
                                                                 .flatMap(Collection::stream)
                                                                 .map(p -> map(p, parameterData.getCalculationType()))
                                                                 .collect(Collectors.toSet())
                                                    , parameterData.getCalculationType()));
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        setSequentialParameterNumber(parameters, 1);
        log.info(" ----------------  END calculate ALL -----------------------");
    }

    private void compareParameters(Map<String, CalculatedParameter> parameters
                                 , Map<String, CalculatedParameter> calculatedParameters) {
        log.info(" ");
        log.info(" ----------------       START compare parameters -----------------------");
        log.info("INPUT parameters = {}", parameters);
        log.info("INPUT calculatedParameters = {}", calculatedParameters);
        if (parameters.isEmpty()) {
            log.info("parameters is empty. put All calculatedParameters");
            parameters.putAll(calculatedParameters);
            return;
        }
        String quantityName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<Integer, Integer> coincidences = getNumberOfMatches(calculatedParameters, parameters);
        log.info("INPUT coincidences = {}", coincidences);
        parameters.forEach((k, v) -> {
            if (quantityName.equals(v.getParameterName()) && coincidences.get(v.getMeasurementNumber()) == parameters.size()) {
                log.info("values= {}", v);
                CalculatedParameter calculatedParameter = parameters.get(quantityName);
                CalculatedParameter parameter = parameters.get(quantityName);
                calculatedParameter.setIntegerValue(calculatedParameter.getIntegerValue() + parameter.getIntegerValue());
                sumQuantity(parameters, calculatedParameters);
                calculatedParameters.put(quantityName, calculatedParameter);
            }
        });
        log.info("OUTPUT parameters = {}", parameters);
        log.info(" ----------------       END compare parameters -----------------------");
        log.info(" ");
    }

    private Map<Integer, Integer> getNumberOfMatches(Map<String, CalculatedParameter>parameters
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

    private void sumQuantity(Map<String, CalculatedParameter> parameters, Map<String, CalculatedParameter> calculatedParameters) {
        parameters.forEach((k,v) -> {
            if (v.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculatedParameter quantity = calculatedParameters.get(MeasuredParameterType.valueOf("QUANTITY").label);
                v.setIntegerValue(v.getIntegerValue() + quantity.getIntegerValue());
            }
        });
    }
}