package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculateMeasurementVMSMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculateMeasurementVMSServiceImpl implements CalculateMeasurementVMSService {

    private final ConstParameterMeasurementService constParameter;
    private final CalculateMeasurementVMSMapper mapper;

    @Override
    public void calculation(Map<String, CalculatedParameter> calculatedParameters
            , Set<ParameterMeasurement> parameters
            , ParameterCalculationType calculation) {
        List<CalculatedParameter> measurements = parameters.stream()
                                                           .map( parameter -> map(parameter, calculation))
                                                           .toList();
        switch (calculation) {
            case NO_ACTION -> measurements.forEach(parameter ->
                calculatedParameters.put(parameter.getParameterName(), parameter)
            );
            case SQUARE -> countSquare(calculatedParameters, measurements);
            case MIN -> countMin(calculatedParameters, measurements);
            case MAX -> countMax(calculatedParameters, measurements);
            case MAX_MIN -> countMaxMin(calculatedParameters, measurements);
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
    }


    private void countMin(Map<String, CalculatedParameter> parameters, List<CalculatedParameter> measurements) {
        String quantityName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.QUANTITY));
        measurements.forEach(p -> {
            if (parameters.isEmpty() && p.getParameterName().equals(quantityName) && p.getIntegerValue() > 1) {
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
        log.info(" ");
        log.info("----- START count Max--------");
        log.info("INPUT parameters = {}", parameters);
        log.info("INPUT measurements = {}", measurements);
        String quantityName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.QUANTITY));
        measurements.forEach(p -> {
            CalculatedParameter parameter = parameters.get(p.getParameterName());
            log.info("parameter = {}", parameter);
            log.info("measurement = {}", p);
            if (parameter == null) {
                add(parameters, p);
            } else {
                if (p.getParameterName().equals(quantityName) && p.getIntegerValue() > 1) {
                    sumQuantity(parameters, p, quantityName);
                } else if (!p.getParameterName().equals(quantityName)) {
                    parameter.setMaxValue(Math.max(p.getMaxValue(), parameter.getMaxValue()));
                }
            }
        });
        log.info("----- END count Max--------");
        log.info(" ");
    }

    private void countMaxMin(Map<String, CalculatedParameter> parameters, List<CalculatedParameter> measurements) {
        String quantityName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.QUANTITY));
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

    private void countSquare(Map<String, CalculatedParameter> calculatedParameters, List<CalculatedParameter> measurements) {
        Map<String, CalculatedParameter> parameters = measurements.stream()
                .collect(Collectors.toMap(CalculatedParameter::getParameterName, p -> p));
        String parameterName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.SQUARE));
        String length = constParameter.getParameterName(String.valueOf(MeasuredParameterType.LENGTH));
        String width = constParameter.getParameterName(String.valueOf(MeasuredParameterType.WIDTH));
        String diameter = constParameter.getParameterName(String.valueOf(MeasuredParameterType.DIAMETER));
        String unitMeasurement = constParameter.getUnitMeasurement(String.valueOf(UnitMeasurementType.M_2));
        double square = 0.0;
        if (parameters.get(length) != null) {
            square = parameters.get(length).getMinValue() * parameters.get(width).getMinValue();
        }
        if (parameters.get(diameter) != null) {
            double rad = parameters.get(diameter).getMinValue() / 2;
            square = Math.PI * rad * rad * 100 / 100;
        }
        square /= 1000000;
        calculatedParameters.put(parameterName, mapper.mapToSquare(parameterName, unitMeasurement, square));
        compareParameters(calculatedParameters, parameters);
    }

    private CalculatedParameter map(ParameterMeasurement parameter, ParameterCalculationType calculation) {
        String quantityName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.QUANTITY));
        if (parameter.getParameterName().equals(quantityName)) {
            return mapper.mapToQuantity(parameter);
        }
        switch (calculation) {
            case NO_ACTION, MIN -> {
                return mapper.mapToMinValue(parameter);
            }
            case MAX -> {
                return mapper.mapToMaxValue(parameter);
            }
            case MAX_MIN -> {
                return mapper.mapToMaxMinValue(parameter);
            }
            default ->
                    throw new BadRequestException(String.format("Mapping for calculationType=%s is not supported", calculation));
        }
    }


    private void compareParameters(Map<String, CalculatedParameter> calculatedParameters
            , Map<String, CalculatedParameter> parameters) {
        if (calculatedParameters.isEmpty()) {
            return;
        }
        String quantityName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.QUANTITY));
        Map<Integer, Integer> coincidences = getNumberOfMatches(calculatedParameters, parameters);
        int size = parameters.size();
        parameters.forEach((k, v) -> {
            if (coincidences.get(v.getMeasurementNumber()) == size) {
                CalculatedParameter calculatedParameter = parameters.get(quantityName);
                CalculatedParameter parameter = parameters.get(quantityName);
                calculatedParameter.setIntegerValue(calculatedParameter.getIntegerValue() + parameter.getIntegerValue());
                calculatedParameters.put(quantityName, calculatedParameter);
            }
        });
    }

    private Map<Integer, Integer> getNumberOfMatches(Map<String, CalculatedParameter> calculatedParameters
            , Map<String, CalculatedParameter> parameters) {
        log.info(" ");
        log.info("----- START get number of matches--------");
        log.info("INPUT  calculatedParameters = {}",  calculatedParameters);
        log.info("INPUT parameters = {}", parameters);
        Map<Integer, Integer> coincidences = new HashMap<>();
        String quantityName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.QUANTITY));
        boolean coincidence = true;
        for (CalculatedParameter parameter : calculatedParameters.values()) {
            CalculatedParameter measurement = parameters.get(parameter.getParameterName());
            log.info("parameter = {}", parameter);
            log.info("measurement = {}", measurement);
            if (!parameter.getParameterName().equals(quantityName)) {
                coincidence = Objects.equals(parameter.getMinValue(), measurement.getMinValue());
            }
            if (coincidence) {
                coincidences.merge(parameter.getMeasurementNumber(), 1, Integer::sum);
            }
        }
        log.info("OUT coincidences = {}", coincidences);
        log.info("----- END get number of matches--------");
        return coincidences;
    }

    private void sumQuantity(Map<String, CalculatedParameter> parameters, CalculatedParameter parameter, String quantityName) {
        log.info(" ");
        log.info("----- START sum Quantity-------");
        log.info("INPUT parameters = {}", parameters);
        log.info("INPUT parameter = {}", parameter);
        if (parameters.isEmpty()) {
            add(parameters, parameter);
        } else {
            CalculatedParameter newParameter = parameters.get(quantityName);
            log.info("INPUT quantityName = {}", quantityName);
            if (newParameter == null) {
                parameters.put(parameter.getParameterName(), parameter);
                return;
            }
            newParameter.setIntegerValue(newParameter.getIntegerValue() + parameter.getIntegerValue());
            parameters.put(newParameter.getParameterName(), newParameter);
        }
        log.info("----- END sum Quantity-------");
    }

    private void add(Map<String, CalculatedParameter> parameters, CalculatedParameter parameter) {
        log.info(" ");
        log.info("----- START ADD TO MAP--------");
        log.info("INPUT parameters = {}", parameters);
        log.info("INPUT parameter = {}", parameter);
        String quantityName = constParameter.getParameterName(String.valueOf(MeasuredParameterType.QUANTITY));
        if (parameter.getParameterName().equals(quantityName) && parameter.getIntegerValue() <= 1) {
            return;
        }
        parameters.put(parameter.getParameterName(), parameter);
        log.info("OUT parameters = {}", parameters);
        log.info("----- END ADD TO MAP--------");
    }
}