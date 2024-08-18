package ru.nabokovsg.diagnosedNK.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstUnitMeasurementService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculationMeasurementServiceImpl implements CalculationMeasurementService {

    private final ConstParameterMeasurementService constParameter;
    private final ConstUnitMeasurementService constUnit;

    @Override
    public CalculatedParameter countMin(CalculatedParameter parameterMeasurement, ParameterMeasurement parameter) {
        if (parameter.getValue() == null) {
            throw new NotFoundException(
                    String.format("To calculate the minimum" +
                            ", the measured value must not be zero, measurement=%s", parameter.getValue())
            );
        }
        if (parameterMeasurement.getMinValue() == null || parameter.getValue() > parameterMeasurement.getMinValue()) {
            parameterMeasurement.setMinValue(parameter.getValue());
        }
        return parameterMeasurement;
    }

    @Override
    public CalculatedParameter countMax(CalculatedParameter parameterMeasurement, ParameterMeasurement parameter) {
        if (parameter.getValue() == null) {
            throw new NotFoundException(
                    String.format("To calculate the minimum" +
                            ", the measured value must not be zero, measurement=%s", parameter.getValue())
            );
        }
        if (parameterMeasurement.getMaxValue() == null || parameterMeasurement.getMaxValue() < parameter.getValue()) {
            parameterMeasurement.setMaxValue(parameter.getValue());
        }
        return parameterMeasurement;
    }

    @Override
    public Set<CalculatedParameter> countQuantity(Map<Long, CalculatedParameter> parameterMeasurements
                                                , Map<String, ParameterMeasurement> parameters) {
        String parameterName = constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY));
        String unitMeasurement = constUnit.get(String.valueOf(UnitMeasurementType.PIECES));
        Double firstValue = parameters.get(parameterName).getValue();
        CalculatedParameter quantity = parameterMeasurements.get(parameters.get(parameterName).getParameterId());
        if (quantity == null) {
            quantity = new CalculatedParameter(null, null, null, parameterName, null, null, unitMeasurement, null, null);
        }
        if (quantity.getMinValue() == null && firstValue == null) {
            quantity.setMinValue(1.0);
        } else {
            if (firstValue != null) {
                quantity.setMinValue(quantity.getMinValue() + firstValue);
            } else {
                quantity.setMinValue(quantity.getMinValue() + 1.0);
            }
        }
        parameterMeasurements.put(quantity.getId(), quantity);
        return new HashSet<>(parameterMeasurements.values());
    }

    @Override
    public Integer getQuantity(Set<ParameterMeasurement> parameterMeasurements
                             , List<ParameterMeasurementDto> parameters) {
        int quantity = 1;
        if (parameterMeasurements == null) {
            return quantity;
        }
        String parameterName = constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY));
        Map<Long, ParameterMeasurementDto> parametersDto = parameters.stream()
                                            .collect(Collectors.toMap(ParameterMeasurementDto::getParameterId, p -> p));
        for (ParameterMeasurement parameter : parameterMeasurements) {
            if (parameter.getParameterName().equals(parameterName)) {
                ParameterMeasurementDto param = parametersDto.get(parameter.getParameterId());
                if (param != null) {
                    quantity = (int) (parameter.getValue() + parameter.getValue());
                } else {
                    quantity = (int) (parameter.getValue() + 1.0);
                }
            }
        }
        return quantity;
    }

    @Override
    public Set<CalculatedParameter> countSquare(Map<Long, CalculatedParameter> parameterMeasurements
                                              , Map<String, ParameterMeasurement> parameters) {
        String measuredParameter = constParameter.get(String.valueOf(MeasuredParameterType.SQUARE));
        String unitMeasurement = constUnit.get(String.valueOf(UnitMeasurementType.M_2));
        String length = constParameter.get(String.valueOf(MeasuredParameterType.LENGTH));
        String width = constParameter.get(String.valueOf(MeasuredParameterType.WIDTH));
        String height = constParameter.get(String.valueOf(MeasuredParameterType.HEIGHT));
        String diameter = constParameter.get(String.valueOf(MeasuredParameterType.DIAMETER));
        CalculatedParameter parameterMeasurement
                = parameterMeasurements.get(parameters.get(measuredParameter).getParameterId());
        Double square = parameters.get(measuredParameter).getValue();
        if (square == null) {
            if (parameters.get(length) != null) {
                if (parameters.get(width) != null) {
                    square = parameters.get(length).getValue() * parameters.get(width).getValue();
                }
                if (parameters.get(height) != null && parameters.get(width) == null) {
                    square = parameters.get(length).getValue() * parameters.get(height).getValue();
                }
            }
            if (parameters.get(diameter) != null) {
                double rad = parameters.get(diameter).getValue() / 2;
                if (parameters.get(height) != null) {
                    square = 2 * Math.PI * rad * parameters.get(height).getValue() * 100 / 100;
                } else {
                    square = Math.PI * rad * rad * 100 / 100;
                }
            }
            if (square != null && parameterMeasurement.getUnitMeasurement().equals(unitMeasurement)) {
                square /= 1000000;
            }
            parameterMeasurement.setMinValue(square);
            parameterMeasurements.put(parameterMeasurement.getId(), parameterMeasurement);
            if (Objects.equals(square, parameterMeasurement.getMinValue())) {
                return countQuantity(parameterMeasurements, parameters);
            }
        }
        return new HashSet<>(parameterMeasurements.values());
    }
}