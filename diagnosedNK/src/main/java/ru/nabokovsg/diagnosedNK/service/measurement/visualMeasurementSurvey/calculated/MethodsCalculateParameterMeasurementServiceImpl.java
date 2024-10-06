package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MethodsCalculateParameterMeasurementServiceImpl implements MethodsCalculateParameterMeasurementService {

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
    public Double countArea(Set<CalculateParameterMeasurement> measurements) {
        Map<String, CalculateParameterMeasurement> parameters = measurements.stream()
                .collect(Collectors.toMap(CalculateParameterMeasurement::getParameterName, p -> p));
        String length = MeasuredParameterType.valueOf("LENGTH").label;
        String width = MeasuredParameterType.valueOf("WIDTH").label;
        String diameter = MeasuredParameterType.valueOf("DIAMETER").label;
        double area = 0.0;
        if (parameters.get(length) != null) {
            area = parameters.get(length).getMinValue() * parameters.get(width).getMinValue();
        }
        if (parameters.get(length) == null && parameters.get(diameter) != null) {
            double rad = parameters.get(diameter).getMinValue() / 2;
            area = Math.PI * rad * rad * 100 / 100;
        }
        return area / 1000000;
    }
}