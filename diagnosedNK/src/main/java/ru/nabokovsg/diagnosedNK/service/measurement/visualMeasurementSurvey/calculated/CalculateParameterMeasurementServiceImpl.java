package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated.CalculatedParameterRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculateAllParametersService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation.CalculateOneByOneParametersService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CalculateParameterMeasurementServiceImpl implements CalculateParameterMeasurementService {

    private final CalculatedParameterRepository repository;
    private final CalculatedParameterMapper mapper;
    private final CalculateOneByOneParametersService calculateOneByOneParametersService;
    private final CalculateAllParametersService calculateAllParametersService;

    @Override
    public void save(CalculatedParameterData parameterData) {
        Map<String, CalculateParameterMeasurement> calculatedParameters = new HashMap<>();
        Set<CalculateParameterMeasurement> parameters = getAll(parameterData);
        Map<String, CalculateParameterMeasurement> calculatedParameters = calculate(parameterData);
        mapWith(parameterData, calculatedParameters);
        if (parameters != null) {
            update(parameters, calculatedParameters);
        }
        repository.saveAll(calculatedParameters.values());
    }

    private void update(Set<CalculateParameterMeasurement> parameters, Map<String, CalculateParameterMeasurement> calculatedParameters) {
        parameters.forEach(parameter -> calculatedParameters.put(parameter.getParameterName()
                      , mapper.mapToUpdateCalculatedParameter(parameter, calculatedParameters.get(parameter.getParameterName()))));
    }

    private void mapWith(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> calculatedParameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT ->
                calculatedParameters.forEach((k,v) ->
                    calculatedParameters.put(k, mapper.mapWithDefect(v, parameterData.getDefect()))
               );
            case REPAIR -> calculatedParameters.forEach((k,v) ->
                    calculatedParameters.put(k, mapper.mapWithRepair(v, parameterData.getRepair()))
            );
            default -> throw new NotFoundException(String.format("Type data=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private Set<CalculateParameterMeasurement> getAll(CalculatedParameterData parameterData) {
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

    private Map<String, CalculateParameterMeasurement> calculate(CalculatedParameterData parameterData) {
        Map<String, CalculateParameterMeasurement> parameters = new HashMap<>();
        switch (parameterData.getCalculationType()) {
            case SQUARE -> calculateOneByOneParametersService.calculateOneByOne(parameterData, parameters);
            case MIN, MAX, MAX_MIN -> calculateAllParametersService.calculateAll(parameterData, parameters);
            default -> throw new NotFoundException(String.format("Completed repair calculation type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        return parameters;
    }
}