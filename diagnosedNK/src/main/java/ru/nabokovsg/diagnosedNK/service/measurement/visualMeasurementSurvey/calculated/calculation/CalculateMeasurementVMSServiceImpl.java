package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculateMeasurementVMSMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateMeasurementVMSServiceImpl implements CalculateMeasurementVMSService {

    private final CalculateMeasurementVMSMapper mapper;

    @Override
    public void countMin(Map<String, CalculatedParameter> calculatedParameters, Set<CalculatedParameter> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculatedParameter parameter = calculatedParameters.get(p.getParameterName());
                if (parameter == null) {
                    calculatedParameters.put(p.getParameterName(), p);
                } else {
                    parameter.setMinValue(Math.min(p.getMinValue(), parameter.getMinValue()));
                }
            }
        });
    }

    @Override
    public void countMax(Map<String, CalculatedParameter> calculatedParameters, Set<CalculatedParameter> measurements) {
        log.info(" ");
        log.info("START countMax");
        log.info("INPUT calculatedParameters = {}", calculatedParameters.values());
        log.info("INPUT measurements = {}", measurements);
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculatedParameter parameter = calculatedParameters.get(p.getParameterName());
                if (parameter == null) {
                    calculatedParameters.put(p.getParameterName(), p);
                } else {
                    log.info("p = {}", p);
                    log.info("parameter = {}",parameter);
                    parameter.setMaxValue(Math.max(p.getMaxValue(), parameter.getMaxValue()));
                }
            }
        });
        log.info("OUTPUT calculatedParameters = {}", calculatedParameters.values());
    }

    @Override
    public void countMaxMin(Map<String, CalculatedParameter> calculatedParameters, Set<CalculatedParameter> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculatedParameter parameter = calculatedParameters.get(p.getParameterName());
                if (parameter == null) {
                    calculatedParameters.put(p.getParameterName(), p);
                } else {
                    parameter.setMinValue(Math.min(p.getMinValue(), parameter.getMinValue()));
                    parameter.setMaxValue(Math.max(p.getMaxValue(), parameter.getMaxValue()));
                }
            }
        });
    }

    @Override
    public void countSquare(Map<String, CalculatedParameter> calculatedParameters, Set<CalculatedParameter> measurements) {
        Map<String, CalculatedParameter> parameters = measurements.stream()
                .collect(Collectors.toMap(CalculatedParameter::getParameterName, p -> p));
        String length = MeasuredParameterType.valueOf("LENGTH").label;
        String width = MeasuredParameterType.valueOf("WIDTH").label;
        String diameter = MeasuredParameterType.valueOf("DIAMETER").label;
        double calculatedSquare = 0.0;
        if (parameters.get(length) != null) {
            calculatedSquare = parameters.get(length).getMinValue() * parameters.get(width).getMinValue();
        }
        if (parameters.get(length) == null && parameters.get(diameter) != null) {
            double rad = parameters.get(diameter).getMinValue() / 2;
            calculatedSquare = Math.PI * rad * rad * 100 / 100;
        }
        calculatedSquare /= 1000000;
        calculatedParameters.put(MeasuredParameterType.valueOf("SQUARE").label
                               , mapper.mapToSquare(MeasuredParameterType.valueOf("SQUARE").label
                                                  , UnitMeasurementType.valueOf("M_2").label
                                                  , calculatedSquare));
    }
}