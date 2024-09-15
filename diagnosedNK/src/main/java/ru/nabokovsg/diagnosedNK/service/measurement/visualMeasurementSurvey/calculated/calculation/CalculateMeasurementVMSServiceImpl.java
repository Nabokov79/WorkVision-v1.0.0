package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculateMeasurementVMSMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateMeasurementVMSServiceImpl implements CalculateMeasurementVMSService {

    private final CalculateMeasurementVMSMapper mapper;


    @Override
    public Map<String, CalculatedParameter> calculation(Set<CalculatedParameter> parameters, ParameterCalculationType calculation) {
        log.info(" ");
        log.info(" ----------------       Class : CalculateMeasurementVMSServiceImpl -----------------------");
        log.info(" ----------------       START calculate parameters-----------------------");
        log.info("INPUT parameters = {}", parameters);
        log.info(" ----------------       START calculate parameters-----------------------");
        Map<String, CalculatedParameter> calculatedParameters = new HashMap<>();
        switch (calculation) {
            case NO_ACTION -> parameters.forEach(parameter ->
                calculatedParameters.put(parameter.getParameterName(), parameter)
            );
            case SQUARE -> countSquare(calculatedParameters, parameters);
            case MIN -> countMin(calculatedParameters, parameters);
            case MAX -> countMax(calculatedParameters, parameters);
            case MAX_MIN -> countMaxMin(calculatedParameters, parameters);
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
        log.info("INPUT calculatedParameters = {}", calculatedParameters);
        log.info(" ----------------       END calculate parameters-----------------------");
        return calculatedParameters;
    }


    private void countMin(Map<String, CalculatedParameter> parameters, Set<CalculatedParameter> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculatedParameter parameter = parameters.get(p.getParameterName());
                if (parameter == null) {
                    parameters.put(p.getParameterName(), p);
                } else {
                    parameter.setMinValue(Math.min(p.getMinValue(), parameter.getMinValue()));
                }
            } else {
                countQuantity(parameters, p);
            }
        });
    }

    private void countMax(Map<String, CalculatedParameter> parameters, Set<CalculatedParameter> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculatedParameter parameter = parameters.get(p.getParameterName());
                if (parameter == null) {
                    parameters.put(p.getParameterName(), p);
                } else {
                    parameter.setMaxValue(Math.max(p.getMaxValue(), parameter.getMaxValue()));
                }
            } else {
                countQuantity(parameters, p);
            }
        });
    }

    private void countMaxMin(Map<String, CalculatedParameter> parameters, Set<CalculatedParameter> measurements) {
        measurements.forEach(p -> {
            if (!p.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                CalculatedParameter parameter = parameters.get(p.getParameterName());
                if (parameter == null) {
                    parameters.put(p.getParameterName(), p);
                } else {
                    parameter.setMinValue(Math.min(p.getMinValue(), parameter.getMinValue()));
                    parameter.setMaxValue(Math.max(p.getMaxValue(), parameter.getMaxValue()));
                }
            } else {
                countQuantity(parameters, p);
            }
        });
    }

    private void countSquare(Map<String, CalculatedParameter> calculatedParameters, Set<CalculatedParameter> measurements) {
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

    private void countQuantity(Map<String, CalculatedParameter> parameters, CalculatedParameter calculatedParameter) {
        if (parameters.isEmpty() && calculatedParameter.getIntegerValue() > 1) {
           parameters.put(calculatedParameter.getParameterName(), calculatedParameter);
        } else {
            parameters.forEach((k,v) -> {
                if (v.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                    v.setIntegerValue(v.getIntegerValue() + calculatedParameter.getIntegerValue());
                }
            });
        }
    }
}