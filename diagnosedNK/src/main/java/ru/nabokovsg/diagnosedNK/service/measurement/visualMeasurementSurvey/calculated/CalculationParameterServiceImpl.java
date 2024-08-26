package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationParameterServiceImpl implements CalculationParameterService {

    private final CalculatedParameterMapper mapper;
    private final MethodCalculateService calculationService;
    private final ConstParameterMeasurementService measurementService;

    @Override
    public List<CalculatedParameter> calculateByDefect(Set<IdentifiedDefect> defects
            , ParameterCalculationType calculation) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        int measurementNumber = 1;
        if (calculation.equals(ParameterCalculationType.QUANTITY)) {
            set(new ArrayList<>(calculationService.countQuantity(defects.stream()
                            .map(IdentifiedDefect::getQuantity)
                            .collect(Collectors.toSet())))
                    , measurementNumber)
                    .forEach(p -> parameters.put(getKey(p.getMeasurementNumber(), p.getParameterName()), p)
                    );
        } else {
            for (IdentifiedDefect defect : defects) {
                calculate(parameters, defect.getParameterMeasurements()
                        , calculation, measurementNumber, defect.getQuantity());
                measurementNumber++;
            }
        }
        return new ArrayList<>(parameters.values());
    }

    @Override
    public List<CalculatedParameter> calculateByRepair(Set<CompletedRepair> repairs
            , ParameterCalculationType calculation) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        int measurementNumber = 1;
        if (calculation.equals(ParameterCalculationType.QUANTITY)) {
            set(new ArrayList<>(calculationService.countQuantity(repairs.stream()
                            .map(CompletedRepair::getQuantity)
                            .collect(Collectors.toSet())))
                    , measurementNumber)
                    .forEach(p -> parameters.put(getKey(p.getMeasurementNumber(), p.getParameterName()), p)
                    );
        } else {
            for (CompletedRepair repair : repairs) {
                calculate(parameters, repair.getParameterMeasurements()
                        , calculation, measurementNumber, repair.getQuantity());
                measurementNumber++;
            }
        }
        return new ArrayList<>(parameters.values());
    }

    private String getKey(Integer measurementNumber, String parameterName) {
        return String.join("", parameterName, String.valueOf(measurementNumber));
    }

    private void calculate(Map<String, CalculatedParameter> parameters
            , Set<ParameterMeasurement> parameterMeasurements
            , ParameterCalculationType calculation
            , int measurementNumber
            , Integer quantity) {
        List<CalculatedParameter> newParameters = set(calculation(parameterMeasurements, calculation, measurementNumber, quantity)
                , measurementNumber);
        log.info("parameters size={}", parameters.size());
        log.info("newParameters size={}", newParameters.size());
        if (calculation.equals(ParameterCalculationType.SQUARE)) {
            searchDuplicate(parameters, newParameters);
        }

    }

    private List<CalculatedParameter> calculation(Set<ParameterMeasurement> parameterMeasurements, ParameterCalculationType calculation, int measurementNumber, Integer quantity) {
        List<CalculatedParameter> parameters = calculation(parameterMeasurements, calculation, measurementNumber, quantity);
        switch (calculation) {
            case NO_ACTION -> {
                parameters.addAll(parameterMeasurements.stream().map(mapper::mapToCalculatedParameter).toList());
            }
            case SQUARE -> {
                parameters.addAll(calculationService.countSquare(parameterMeasurements));
            }
            case MIN -> {
                parameters.addAll(calculationService.countMin(parameterMeasurements));
            }
            case MAX -> {
                parameters.addAll(calculationService.countMax(parameterMeasurements));
            }
            case MAX_MIN -> {
                parameters.addAll(calculationService.countMaxMin(parameterMeasurements));
            }
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
        if (quantity != null && quantity > 1) {
            parameters.add(calculationService.createQuantityParameter(quantity));
        }
        return set(parameters, measurementNumber);
    }

    private void searchDuplicate(Map<String, CalculatedParameter> parameters, List<CalculatedParameter> newParameters) {
        log.info("size={}", parameters.size());
        if (parameters.size() > 1) {
            log.info("start search duplicate");
            Map<String, CalculatedParameter> calculatedParameters = newParameters.stream()
                    .collect(Collectors.toMap(CalculatedParameter::getParameterName, p -> p));
            Map<Integer, Integer> coincidences = new HashMap<>();
            String quantity = measurementService.get(String.valueOf(MeasuredParameterType.QUANTITY));
            parameters.forEach((k, v) -> {
                CalculatedParameter parameter = calculatedParameters.get(k);
                if (parameter != null && Objects.equals(parameter.getMinValue(), v.getMinValue())) {
                    coincidences.merge(v.getMeasurementNumber(), 1, Integer::sum);
                }
                if (coincidences.get(v.getMeasurementNumber()) == calculatedParameters.size()) {
                    String key = String.join("", quantity, String.valueOf(v.getMeasurementNumber()));
                    CalculatedParameter quantityParameter = parameters.get(key);
                    quantityParameter.setMinValue(quantityParameter.getMinValue()
                            + calculatedParameters.get(measurementService.get(quantity)).getMinValue());
                    parameters.put(key, quantityParameter);
                }
            });
        }
    }

    private List<CalculatedParameter> set(List<CalculatedParameter> parameters, Integer measurementNumber) {
        int sequentialNumber = 1;
        String square = measurementService.get(String.valueOf(MeasuredParameterType.SQUARE));
        String quantity = measurementService.get(String.valueOf(MeasuredParameterType.QUANTITY));
        int size = parameters.size();
        for (CalculatedParameter parameter : parameters) {
            if (parameter.getMeasurementNumber() == null) {
                if (parameter.getParameterName().equals(square)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                } else if (parameter.getParameterName().equals(quantity)) {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, size);
                } else {
                    mapper.mapWithSequenceNumber(parameter, measurementNumber, sequentialNumber);
                    sequentialNumber++;
                }
            }
        }
        return parameters;
    }
}