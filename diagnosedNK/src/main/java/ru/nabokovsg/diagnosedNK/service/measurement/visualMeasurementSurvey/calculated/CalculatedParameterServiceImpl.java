package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculateAllParametersService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculateOneByOneParametersService;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculatedParameterServiceImpl implements CalculatedParameterService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculateOneByOneParametersService calculateOneByOneParametersService;
    private final CalculateAllParametersService calculateAllParametersService;

    @Override
    public void save(CalculatedParameterData parameterData) {
        Set<CalculatedParameter> parametersDb = getAll(parameterData);
        Map<String, CalculatedParameter> calculatedParameters = calculate(parameterData);
        log.info(" ");
        log.info("START save CalculatedParameter :");
        log.info(String.format("INPUT DATA : Identified defects=%s", parameterData.getDefects()));
        log.info(String.format("INPUT DATA : CalculatedParameter defect=%s", parameterData.getDefect()));
        log.info(String.format("INPUT DATA : CalculatedParameter parametersDb=%s", parameterData.getDefect().getParameters()));
        mapTo(parameterData, calculatedParameters);
        if (parametersDb != null) {
            update(parametersDb, calculatedParameters);
        }
        log.info(String.format("Data for save=%s", calculatedParameters.values()));
        repository.saveAll(calculatedParameters.values());
    }

    private void update(Set<CalculatedParameter> parametersDb, Map<String, CalculatedParameter> calculatedParameters) {
        parametersDb.forEach(parameter -> calculatedParameters.put(parameter.getParameterName()
                      , mapper.mapToUpdateCalculatedParameter(parameter, calculatedParameters.get(parameter.getParameterName()))));
    }

    private void mapTo(CalculatedParameterData parameterData, Map<String, CalculatedParameter> calculatedParameters) {
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

    private Map<String, CalculatedParameter> calculate(CalculatedParameterData parameterData) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        switch (parameterData.getCalculationType()) {
            case SQUARE -> calculateOneByOneParametersService.calculateOneByOne(parameterData, parameters);
            case MIN, MAX, MAX_MIN -> calculateAllParametersService.calculateAll(parameterData, parameters);
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        return parameters;
    }
}