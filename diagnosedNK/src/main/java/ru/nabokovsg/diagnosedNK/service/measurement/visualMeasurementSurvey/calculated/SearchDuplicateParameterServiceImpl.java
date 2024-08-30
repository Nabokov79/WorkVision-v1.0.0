package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchDuplicateParameterServiceImpl implements SearchDuplicateParameterService {

    private final ConstParameterMeasurementService measurementService;
    @Override
    public void search(Map<String, CalculatedParameter> parametersDb, List<CalculatedParameter> calculatedParameters) {
        if (parametersDb.isEmpty()) {
            return;
        }
        Map<String, CalculatedParameter> parameters = calculatedParameters.stream()
                .collect(Collectors.toMap(CalculatedParameter::getParameterName, p -> p));
        Map<Integer, Integer> coincidences = getNumberOfMatches(parameters,parameters);
        String quantity = measurementService.get(String.valueOf(MeasuredParameterType.QUANTITY));
        if (!coincidences.isEmpty()) {
            coincidences.forEach((k, v) -> {
                if (v == parameters.size()) {
                    String key = getKey(k, quantity);
                    CalculatedParameter quantityParameter = parameters.get(key);
                    CalculatedParameter quantityParameterDb = parametersDb.get(key);
                    if (quantityParameterDb != null) {
                        quantityParameter.setMinValue(quantityParameter.getMinValue() + quantityParameterDb.getMinValue());
                    }
                    parameters.put(getKey(k, quantity), quantityParameter);
                }
            });
            parameters.clear();
        }
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
        return coincidences;
    }

    private String getKey(Integer measurementNumber, String parameterName) {
        return String.join("", parameterName, String.valueOf(measurementNumber));
    }
}