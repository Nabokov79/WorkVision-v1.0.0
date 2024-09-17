package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.ParameterCalculationManagerService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final  ParameterCalculationManagerService calculationManagerService;

    @Override
    public void save(CalculatedParameterData parameterData) {
        log.info(" ");
        log.info("Start calculation parameters");
        Set<CalculatedParameter> parametersDb = getAll(parameterData);
        Map<String, CalculatedParameter> calculatedParameters = calculationManagerService.calculate(parameterData);
        log.info("Before calculation :");
        log.info("calculatedParameters = {}", calculatedParameters.values());
        mapTo(parameterData, calculatedParameters);
        if (parametersDb != null) {
            update(parametersDb, calculatedParameters);
        }
        log.info("save to db");
        repository.saveAll(calculatedParameters.values());
        log.info("End calculation parameters");
    }

    private void update(Set<CalculatedParameter> parametersDb, Map<String, CalculatedParameter> calculatedParameters) {
        log.info(" ");
        log.info("Start update parameters");
        parametersDb.forEach(parameter -> calculatedParameters.put(parameter.getParameterName()
                      , mapper.mapToUpdateCalculatedParameter(parameter, calculatedParameters.get(parameter.getParameterName()))));
        log.info("End update parameters");
    }

    private void mapTo(CalculatedParameterData parameterData,Map<String, CalculatedParameter> calculatedParameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT ->
                calculatedParameters.forEach((k,v) ->
                    calculatedParameters.put(k, mapper.mapWithDefect(v, parameterData.getDefect()))
               );
            case REPAIR -> calculatedParameters.forEach((k,v) ->
                    calculatedParameters.put(k, mapper.mapWithRepair(v, parameterData.getRepair()))
            );
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private Set<CalculatedParameter> getAll(CalculatedParameterData parameterData) {
        switch (parameterData.getTypeData()) {
            case DEFECT -> {
                return parameterData.getDefect().getParameters();
            }
            case REPAIR -> {
                return parameterData.getRepair().getParameters();
            }
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }
}