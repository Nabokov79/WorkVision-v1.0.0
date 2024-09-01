package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculationParameterService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculationParameterService calculationService;
    private final ConstParameterMeasurementService measurementService;
    private final static Integer MEASUREMENT = 1;

    @Override
    public void save(CalculatedParameterData parameterData) {
        log.info("-----------Start SAVE---------------------");
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        if (parameterData.getDefects() != null && parameterData.getRepairs() == null) {
            calculateByDefect(parameters, parameterData);
        } else if (parameterData.getRepairs() != null && parameterData.getDefects() == null) {
            calculateByRepair(parameters, parameterData);
        } else {
            throw new BadRequestException(
                    String.format("Defects=%s and Repairs=%s not be null", parameterData.getDefects()
                            , parameterData.getRepairs()));
        }

        log.info("-----------End SAVE---------------------");
        repository.saveAll(parameters.values());
    }

    private void mapWithDefect(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        List<CalculatedParameter> parametersDb = convertParameters(parameterData.getDefect().getParameters());
        log.info("          ");
        log.info("-----------start map with defect---------------------");
        log.info(String.format("CalculatedParameter defect parameters = %s",parameterData.getDefect().getParameters()));
        log.info(String.format("parameters = %s",parameters.values()));
        log.info("   ");
        parameters.forEach((k, v) -> {
            if (parametersDb.isEmpty()) {
                parameters.put(k, mapper.mapWithDefect(v, null, parameterData.getDefect()));
            } else {
                parameters.put(k, mapper.mapWithDefect(v, parametersDb.get(0), parameterData.getDefect()));
                parametersDb.remove(0);
            }
        });
        log.info(" BEFORE MAP  ");
        log.info(String.format("CalculatedParameter defect parameters = %s",parameterData.getDefect().getParameters()));
        log.info(String.format("parameters = %s",parameters.values()));
        log.info("-----------End map with defect---------------------");
        log.info("          ");
    }

    private void mapWithRepair(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        List<Long> ids = getIds(parameterData.getRepair().getParameters());
        parameters.forEach((k, v) -> parameters.put(k, mapper.mapWithRepair(v, parameterData.getRepair())));
    }

    private void calculateByDefect(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        int measurementNumber = MEASUREMENT;
        List<Integer> quantity = parameterData.getDefects()
                .stream()
                .map(IdentifiedDefect::getQuantity)
                .toList();
        for (IdentifiedDefect defect : parameterData.getDefects()) {
            log.info(String.format("IdentifiedDefect Parameters = %s", defect.getParameterMeasurements()));
            Map<String, CalculatedParameter> calculatedParameters = calculationService.calculation(defect.getParameterMeasurements()
                    , parameterData.getCalculationType()
                    , measurementNumber
                    , defect.getQuantity()
                    , quantity);
            Map<Integer, Integer> coincidences = getNumberOfMatches(parameters, calculatedParameters);
            if (parameterData.getDefect().getParameters() == null && coincidences.isEmpty()) {
                addAll(parameters, calculatedParameters.values().stream().toList());
            } else {
                compareParameters(parameters, calculatedParameters, coincidences);
            }
            measurementNumber++;
        }
        mapWithDefect(parameters, parameterData);
    }

    private void calculateByRepair(Map<String, CalculatedParameter> parameters, CalculatedParameterData parameterData) {
        int measurementNumber = MEASUREMENT;
        List<Integer> quantity = parameterData.getRepairs()
                .stream()
                .map(CompletedRepair::getQuantity)
                .toList();
        for (CompletedRepair repair : parameterData.getRepairs()) {
            Map<String, CalculatedParameter> calculatedParameters = calculationService.calculation(repair.getParameterMeasurements()
                    , parameterData.getCalculationType()
                    , measurementNumber
                    , repair.getQuantity()
                    , quantity);
            Map<Integer, Integer> coincidences = getNumberOfMatches(parameters, calculatedParameters);
            if (parameterData.getDefect().getParameters() == null && coincidences.isEmpty()) {
                addAll(parameters, calculatedParameters.values().stream().toList());
            } else {
                compareParameters(parameters, calculatedParameters, coincidences);
            }
            measurementNumber++;
        }
        mapWithRepair(parameters, parameterData);
    }

    private void addAll(Map<String, CalculatedParameter> parameters, List<CalculatedParameter> calculatedParameters) {
        calculatedParameters.forEach(p -> parameters.put(getKey(p.getMeasurementNumber(), p.getParameterName()), p));
    }

    private void compareParameters(Map<String, CalculatedParameter> parameters, Map<String, CalculatedParameter> calculatedParameters,  Map<Integer, Integer> coincidences) {
        log.info("          ");
        log.info("-----------start compare parameters---------------------");
        log.info("-----------INPUT DATA---------------------");
        log.info("Parameters: key = {}", parameters.keySet());
        log.info("Parameters: values = {}", parameters.values());
        log.info("CalculatedParameters: key = {}", calculatedParameters.keySet());
        log.info("CalculatedParameters: values = {}", calculatedParameters.values());
        String quantityName = measurementService.get(String.valueOf(MeasuredParameterType.QUANTITY));
        parameters.forEach((k, v) -> {
                    Integer coincidence = coincidences.get(v.getMeasurementNumber());
                    if (coincidence == calculatedParameters.size()) {
                        CalculatedParameter parameter = calculatedParameters.get(v.getParameterName());
                        parameter.setMinValue(v.getMinValue() + parameter.getMinValue());
                        parameters.put(k, parameter);
                    }
                });
        log.info(String.format("parameters = %s",parameters.values()));
        log.info("-----------end compare parameters---------------------");
        log.info("       ");
    }

    private String getKey(Integer measurementNumber, String parameterName) {
        return String.join("_", parameterName, String.valueOf(measurementNumber));
    }

    private Map<Integer, Integer> getNumberOfMatches(Map<String, CalculatedParameter> parameters
                                                   , Map<String, CalculatedParameter> calculatedParameters) {
        Map<Integer, Integer> coincidences = new HashMap<>();
        parameters.forEach((k, v) -> {
            CalculatedParameter parameter = calculatedParameters.get(v.getParameterName());
            if (parameter != null && Objects.equals(parameter.getMinValue(), v.getMinValue())) {
                coincidences.merge(v.getMeasurementNumber(), 1, Integer::sum);
            }
        });
        log.info("coincidences: key = {}", coincidences.keySet());
        log.info("coincidences: values = {}", coincidences.values());
        return coincidences;
    }

    private List<Long> getIds(Set<CalculatedParameter> parameters) {
        if (parameters == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(parameters.stream()
                .map(CalculatedParameter::getId)
                .toList());
    }

    private List<CalculatedParameter> convertParameters(Set<CalculatedParameter> parameters) {
        List<CalculatedParameter> parametersDb = new ArrayList<>();
        if (parameters != null) {
            parametersDb.addAll(parameters);
        }
        return parametersDb;
    }
}