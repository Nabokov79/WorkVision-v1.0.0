package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculationMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstUnitMeasurementService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateMeasurementVMSServiceImpl implements CalculateMeasurementVMSService {

    private final ConstParameterMeasurementService constParameter;
    private final ConstUnitMeasurementService constUnit;
    private final CalculationMeasurementMapper mapper;

    @Override
    public List<CalculatedParameter> calculation(Map<String, CalculatedParameter> calculatedParameters
            , Set<ParameterMeasurement> parameters
            , ParameterCalculationType calculation) {
        String quantityName = constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY));
        switch (calculation) {
            case NO_ACTION ->
                    map(parameters).forEach(parameter -> calculatedParameters.put(parameter.getParameterName(), parameter));
            case SQUARE -> calculateMeasurementService.countSquare(parameters.stream().map(p -> {
                if (p.getParameterName().equals(quantityName)) {
                    return mapper.mapToQuantity(p);
                } else {
                    return mapper.mapToMinValue(p);
                }
            }).toList());
            case MIN -> countMin(calculatedParameters, map(parameters));
            case MAX -> countMax(calculatedParameters, map(parameters));
            case MAX_MIN -> countMaxMin(calculatedParameters, map(parameters));
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
        return calculatedParameters;
    }


    private void countMin(Map<String, CalculatedParameter> parameters, List<CalculatedParameter> measurements) {
        String quantityName = constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY));
        measurements.forEach(p -> {
            if (parameters.isEmpty()) {
                add(parameters, p);
            } else if (p.getParameterName().equals(quantityName)) {
                sumQuantity(parameters, p, quantityName);
            } else {
                CalculatedParameter parameter = parameters.get(p.getParameterName());
                parameter.setMinValue(Math.min(p.getMinValue(), parameter.getMinValue()));
            }
        });
    }

    private void countMax(Map<String, CalculatedParameter> parameters, List<CalculatedParameter> measurements) {
        String quantityName = constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY));
        measurements.forEach(p -> {
            if (parameters.isEmpty()) {
                add(parameters, p);
            } else if (p.getParameterName().equals(quantityName)) {
                sumQuantity(parameters, p, quantityName);
            } else {
                CalculatedParameter parameter = parameters.get(p.getParameterName());
                parameter.setMaxValue(Math.max(p.getMaxValue(), parameter.getMaxValue()));
            }
        });
    }

    private void countMaxMin(Map<String, CalculatedParameter> parameters, List<CalculatedParameter> measurements) {
        String quantityName = constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY));
        measurements.forEach(p -> {
            if (parameters.isEmpty()) {
                add(parameters, p);
            } else if (p.getParameterName().equals(quantityName)) {
                sumQuantity(parameters, p, quantityName);
            } else {
                CalculatedParameter parameter = parameters.get(p.getParameterName());
                parameter.setMinValue(Math.min(p.getMinValue(), parameter.getMinValue()));
                parameter.setMaxValue(Math.max(p.getMaxValue(), parameter.getMaxValue()));
            }
        });
    }

    private List<CalculatedParameter> map(Set<ParameterMeasurement> parameters) {
        String quantityName = constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY));
        return parameters.stream()
                         .map(p -> {
                             if (p.getParameterName().equals(quantityName)) {
                                 return mapper.mapToQuantity(p);
                             } else {
                                 return mapper.mapToMaxMinValue(p);
                             }
                         })
                        .toList();
    }
    public List<CalculatedParameter> countSquare(List<CalculatedParameter> measurements) {
        List<CalculatedParameter> calculatedParameters = new ArrayList<>();
        Map<String, CalculatedParameter> parameters = measurements.stream()
                .collect(Collectors.toMap(CalculatedParameter::getParameterName, p -> p));
        String parameterName = constParameter.get(String.valueOf(MeasuredParameterType.SQUARE));
        String unitMeasurement = constUnit.get(String.valueOf(UnitMeasurementType.M_2));
        String length = constParameter.get(String.valueOf(MeasuredParameterType.LENGTH));
        String width = constParameter.get(String.valueOf(MeasuredParameterType.WIDTH));
        String diameter = constParameter.get(String.valueOf(MeasuredParameterType.DIAMETER));
        double square = 0.0;
        if (parameters.get(length) != null) {
            square = parameters.get(length).getMinValue() * parameters.get(width).getMinValue();
        }
        if (parameters.get(diameter) != null) {
            double rad = parameters.get(diameter).getMinValue() / 2;
            square = Math.PI * rad * rad * 100 / 100;
        }
        square /= 1000000;
        calculatedParameters.add(mapper.mapToSquare(parameterName, unitMeasurement, square));
        measurements.forEach(parameter -> {
            if (!parameter.getParameterName().equals(length)
                    && !parameter.getParameterName().equals(width)
                    && !parameter.getParameterName().equals(diameter)) {
                calculatedParameters.add(parameter);
            }
        });
        return calculatedParameters;
    }

    private void sumQuantity(Map<String, CalculatedParameter> calculatedParameters, CalculatedParameter parameter, String quantityName) {
        if (calculatedParameters.isEmpty()) {
            add(calculatedParameters, parameter);
        } else {
            CalculatedParameter newParameter = calculatedParameters.get(quantityName);
            newParameter.setIntegerValue(newParameter.getIntegerValue() + parameter.getIntegerValue());
        }
    }

    private void add(Map<String, CalculatedParameter> calculatedParameters, CalculatedParameter parameter) {
        calculatedParameters.put(parameter.getParameterName(), parameter);
    }
}