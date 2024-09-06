package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculationMeasurementMapper;
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
@Slf4j
public class MethodCalculateServiceImpl implements MethodCalculateService {

    private final ConstParameterMeasurementService constParameter;
    private final ConstUnitMeasurementService constUnit;
    private final CalculationMeasurementMapper mapper;

    @Override
    public List<CalculatedParameter> countMin(Set<ParameterMeasurement> measurements) {
        List<CalculatedParameter> calculatedParameters = new ArrayList<>();
        double minValue = 0.0;
        for (ParameterMeasurement parameter : measurements) {
            if (minValue == 0.0 || parameter.getValue() < minValue) {
                minValue = parameter.getValue();
            }
            calculatedParameters.add(mapper.mapToMinValue(parameter, minValue));
        }
        return calculatedParameters;
    }

    @Override
    public List<CalculatedParameter> countMax(Set<ParameterMeasurement> measurements) {
        List<CalculatedParameter> calculatedParameters = new ArrayList<>();
        double maxValue = 0.0;
        for (ParameterMeasurement parameter : measurements) {
            if (maxValue == 0.0 || parameter.getValue() > maxValue) {
                maxValue = parameter.getValue();
            }
            calculatedParameters.add(mapper.mapToMaxValue(parameter, maxValue));
        }
        return calculatedParameters;
    }

    public List<CalculatedParameter> countMaxMin(Set<ParameterMeasurement> measurements) {
        List<CalculatedParameter> calculatedParameters = new ArrayList<>();
        double minValue = 0.0;
        double maxValue = 0.0;
        for (ParameterMeasurement parameter : measurements) {
            if (minValue == 0.0 || parameter.getValue() < minValue) {
                minValue = parameter.getValue();
            }
            if (maxValue == 0.0 || parameter.getValue() > maxValue) {
                maxValue = parameter.getValue();
            }
            calculatedParameters.add(mapper.mapToMaxMinValue(parameter, minValue, maxValue));
        }
        return calculatedParameters;
    }

    @Override
    public Integer getQuantity(Integer quantityDb, Integer quantityDto) {
        int quantity = 1;
        if (quantityDb == null && quantityDto == null) {
            return quantity;
        } else if (quantityDb == null) {
            return quantityDto;
        } else if (quantityDto == null) {
            return quantityDb + quantity;
        } else {
            return quantityDb + quantityDto;
        }
    }

    @Override
    public List<CalculatedParameter> countSquare(Set<ParameterMeasurement> measurements) {
        List<CalculatedParameter> calculatedParameters = new ArrayList<>();
        Map<String, ParameterMeasurement> parameters = measurements.stream()
                                             .collect(Collectors.toMap(ParameterMeasurement::getParameterName, p -> p));
        String parameterName = constParameter.get(String.valueOf(MeasuredParameterType.SQUARE));
        String unitMeasurement = constUnit.get(String.valueOf(UnitMeasurementType.M_2));
        String length = constParameter.get(String.valueOf(MeasuredParameterType.LENGTH));
        String width = constParameter.get(String.valueOf(MeasuredParameterType.WIDTH));
        String diameter = constParameter.get(String.valueOf(MeasuredParameterType.DIAMETER));
        double square = 0.0;
        if (parameters.get(length) != null) {
            square = parameters.get(length).getValue() * parameters.get(width).getValue();
        }
        if (parameters.get(diameter) != null) {
            double rad = parameters.get(diameter).getValue() / 2;
            square = Math.PI * rad * rad * 100 / 100;
        }
        square /= 1000000;
        calculatedParameters.add(mapper.mapToSquare(parameterName, unitMeasurement, square));
        measurements.forEach(parameter -> {
            if(!parameter.getParameterName().equals(length)
               && !parameter.getParameterName().equals(width)
              && !parameter.getParameterName().equals(diameter)) {
                calculatedParameters.add(mapper.mapToMinValue(parameter, parameter.getValue()));
            }
        });
        return calculatedParameters;
    }
}