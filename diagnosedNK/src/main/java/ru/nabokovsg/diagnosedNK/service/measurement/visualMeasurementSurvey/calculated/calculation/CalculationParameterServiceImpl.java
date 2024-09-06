package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CalculationParameterServiceImpl implements CalculationParameterService {

    private final CalculatedParameterMapper mapper;
    private final MethodCalculateService methodCalculateService;

    @Override
    public List<CalculatedParameter> calculation(Set<ParameterMeasurement> parameters
                                               , ParameterCalculationType calculation) {
        List<CalculatedParameter> calculatedParameters = new ArrayList<>();
        switch (calculation) {
            case NO_ACTION, QUANTITY ->
                    calculatedParameters.addAll(parameters.stream().map(mapper::mapToCalculatedParameter).toList());
            case SQUARE ->
                    calculatedParameters.addAll(methodCalculateService.countSquare(parameters));
            case MIN ->
                    calculatedParameters.addAll(methodCalculateService.countMin(parameters));
            case MAX ->
                    calculatedParameters.addAll(methodCalculateService.countMax(parameters));
            case MAX_MIN ->
                    calculatedParameters.addAll(methodCalculateService.countMaxMin(parameters));
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
        return calculatedParameters;
    }
}