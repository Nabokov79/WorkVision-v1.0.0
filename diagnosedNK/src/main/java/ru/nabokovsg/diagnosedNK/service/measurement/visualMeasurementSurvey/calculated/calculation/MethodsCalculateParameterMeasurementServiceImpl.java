package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculateMeasurementVMSMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MethodsCalculateParameterMeasurementServiceImpl implements MethodsCalculateParameterMeasurementService {
    private final CalculateMeasurementVMSMapper mapper;
    public void countQuantity(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        String parameterName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<String, CalculateParameterMeasurement> calculatedParameter = new HashMap<>(1);
        measurements.forEach(v -> {
            if (v.getParameterName().equals(parameterName)) {
                CalculateParameterMeasurement parameter = calculatedParameter.get(parameterName);
                if (parameter == null) {
                    calculatedParameter.put(parameterName, mapper.mapToQuantity(v));
                } else {
                    parameter.setIntegerValue(parameter.getIntegerValue() + v.getIntegerValue());
                    calculatedParameters.put(parameterName, parameter);
                }
            }
        });
        calculatedParameter.forEach((k,v) -> {
            if (v.getIntegerValue() > 1) {
                calculatedParameters.put(k, v);
            }
        });
    }

    @Override
    public void countMin(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculateParameterMeasurement parameter = calculatedParameters.get(p.getParameterName());
                if (parameter == null) {
                    calculatedParameters.put(p.getParameterName(), p);
                } else {
                    parameter.setMinValue(Math.min(p.getMinValue(), parameter.getMinValue()));
                }
            }
        });
    }

    @Override
   public void countMax(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculateParameterMeasurement parameter = calculatedParameters.get(p.getParameterName());
                if (parameter == null) {
                    calculatedParameters.put(p.getParameterName(), p);
                } else {
                    parameter.setMaxValue(Math.max(p.getMaxValue(), parameter.getMaxValue()));
                }
            }
        });
    }

    @Override
    public void countMaxMin(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculateParameterMeasurement parameter = calculatedParameters.get(p.getParameterName());
                if (parameter == null) {
                    calculatedParameters.put(p.getParameterName(), p);
                } else {
                    parameter.setMinValue(Math.min(p.getMinValue(), parameter.getMinValue()));
                    parameter.setMaxValue(Math.max(p.getMaxValue(), parameter.getMaxValue()));
                    if (Objects.equals(parameter.getMinValue(), parameter.getMaxValue())) {
                        parameter.setMinValue(null);
                    }
                }
            }
        });
    }

    @Override
    public double countSquare(Set<CalculateParameterMeasurement> measurements) {
        Map<String, CalculateParameterMeasurement> parameters = measurements.stream()
                .collect(Collectors.toMap(CalculateParameterMeasurement::getParameterName, p -> p));
        String length = MeasuredParameterType.valueOf("LENGTH").label;
        String width = MeasuredParameterType.valueOf("WIDTH").label;
        String diameter = MeasuredParameterType.valueOf("DIAMETER").label;
        Map<Double, String> parameterValue = new HashMap<>(1);


        double calculatedSquare = 0.0;
        if (parameters.get(length) != null) {
            calculatedSquare = parameters.get(length).getMinValue() * parameters.get(width).getMinValue();
        }
        if (parameters.get(length) == null && parameters.get(diameter) != null) {
            double rad = parameters.get(diameter).getMinValue() / 2;
            calculatedSquare = Math.PI * rad * rad * 100 / 100;
        }
        return calculatedSquare /= 1000000;
    }
}