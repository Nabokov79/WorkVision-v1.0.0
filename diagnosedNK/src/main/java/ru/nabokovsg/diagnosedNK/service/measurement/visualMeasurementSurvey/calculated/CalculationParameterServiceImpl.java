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
import ru.nabokovsg.diagnosedNK.model.norms.UnitMeasurementType;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstParameterMeasurementService;
import ru.nabokovsg.diagnosedNK.service.constantService.ConstUnitMeasurementService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationParameterServiceImpl implements CalculationParameterService {

    private final CalculatedParameterMapper mapper;
    private final MethodCalculateService calculationService;
    private final ConstParameterMeasurementService measurementService;
    private final ConstParameterMeasurementService constParameter;
    private final ConstUnitMeasurementService constUnit;

    @Override
    public List<CalculatedParameter> calculateByDefect(Set<IdentifiedDefect> defects
            , ParameterCalculationType calculation) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        int measurementNumber = 1;
        Set<Integer> quantity = defects.stream().map(IdentifiedDefect::getQuantity).collect(Collectors.toSet());
        for (IdentifiedDefect defect : defects) {
            create(parameters
                 , defect.getParameterMeasurements()
                 , calculation
                 , quantity
                 , measurementNumber
                 , defect.getQuantity());
            measurementNumber++;
        }
        return new ArrayList<>(parameters.values());
    }

    @Override
    public List<CalculatedParameter> calculateByRepair(Set<CompletedRepair> repairs
            , ParameterCalculationType calculation) {
        Map<String, CalculatedParameter> parameters = new HashMap<>();
        int measurementNumber = 1;
        Set<Integer> quantity = repairs.stream().map(CompletedRepair::getQuantity).collect(Collectors.toSet());
        for (CompletedRepair repair : repairs) {
            create(parameters
                 , repair.getParameterMeasurements()
                 , calculation
                 , quantity
                 , measurementNumber
                 , repair.getQuantity());
            measurementNumber++;
        }
        return new ArrayList<>(parameters.values());
    }

    private void create(Map<String, CalculatedParameter> parameters
                      , Set<ParameterMeasurement> parameterMeasurements
                      , ParameterCalculationType calculation
                      , Set<Integer> quantityParameters
                      , int measurementNumber
                      , Integer quantity) {
        List<CalculatedParameter> calculatedParameters = calculation(parameterMeasurements
                                                       , calculation
                                                       , quantityParameters);
        if (quantity != null && quantity > 1) {
            calculatedParameters.add(createQuantityParameter(quantity));
        }
        if (calculation.equals(ParameterCalculationType.SQUARE)) {
            searchDuplicate(parameters, calculatedParameters);
            return;
        }
        setSequentialParameterNumber(calculatedParameters, measurementNumber)
                .forEach(p -> parameters.put(getKey(p.getMeasurementNumber(), p.getParameterName()), p));
    }

    private List<CalculatedParameter> calculation(Set<ParameterMeasurement> parameterMeasurements
                                                , ParameterCalculationType calculation
                                                , Set<Integer> quantityParameters) {
        switch (calculation) {
            case NO_ACTION -> {
                return parameterMeasurements.stream().map(mapper::mapToCalculatedParameter).toList();
            }
            case SQUARE -> {
                return calculationService.countSquare(parameterMeasurements);
            }
            case QUANTITY -> {
                return List.of(createQuantityParameter(calculationService.countQuantity(quantityParameters)));
            }
            case MIN -> {
                return calculationService.countMin(parameterMeasurements);
            }
            case MAX -> {
                return calculationService.countMax(parameterMeasurements);
            }
            case MAX_MIN -> {
                return calculationService.countMaxMin(parameterMeasurements);
            }
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
    }

    private String getKey(Integer measurementNumber, String parameterName) {
        return String.join("", parameterName, String.valueOf(measurementNumber));
    }

    private void searchDuplicate(Map<String, CalculatedParameter> parameters, List<CalculatedParameter> newParameters) {
        if (parameters.size() > 1) {
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
                    String key = getKey(v.getMeasurementNumber(), quantity);
                    CalculatedParameter quantityParameter = parameters.get(key);
                    quantityParameter.setMinValue(quantityParameter.getMinValue()
                            + calculatedParameters.get(measurementService.get(quantity)).getMinValue());
                    parameters.put(key, quantityParameter);
                }
            });
        }
    }

    private List<CalculatedParameter> setSequentialParameterNumber(List<CalculatedParameter> parameters
                                                                 , Integer measurementNumber) {
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

    public CalculatedParameter createQuantityParameter(int quantity) {
        return mapper.mapToQuantity(constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY))
                                  , constUnit.get(String.valueOf(UnitMeasurementType.PIECES))
                                  , quantity);
    }
}