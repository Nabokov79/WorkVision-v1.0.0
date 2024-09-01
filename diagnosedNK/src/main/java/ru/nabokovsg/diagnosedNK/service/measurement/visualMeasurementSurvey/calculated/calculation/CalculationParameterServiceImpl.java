package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated.CalculatedParameterMapper;
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
public class CalculationParameterServiceImpl implements CalculationParameterService {

    private final CalculatedParameterMapper mapper;
    private final MethodCalculateService calculationService;
    private final ConstParameterMeasurementService measurementService;
    private final ConstParameterMeasurementService constParameter;
    private final ConstUnitMeasurementService constUnit;

    @Override
    public Map<String, CalculatedParameter> calculation(Set<ParameterMeasurement> parameters
                                               , ParameterCalculationType calculation
                                               , int measurementNumber
                                               , Integer quantity
                                               , List<Integer> quantityParameters) {
        List<CalculatedParameter> calculatedParameters = new ArrayList<>();
        switch (calculation) {
            case NO_ACTION ->
                    calculatedParameters.addAll(parameters.stream().map(mapper::mapToCalculatedParameter).toList());
            case SQUARE ->
                    calculatedParameters.addAll(calculationService.countSquare(parameters));
            case QUANTITY ->
                    calculatedParameters.add(createQuantityParameter(calculationService.countQuantity(quantityParameters)));
            case MIN ->
                    calculatedParameters.addAll(calculationService.countMin(parameters));
            case MAX ->
                    calculatedParameters.addAll(calculationService.countMax(parameters));
            case MAX_MIN ->
                    calculatedParameters.addAll(calculationService.countMaxMin(parameters));
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
        addQuantity(calculatedParameters, calculation, quantity);
        return setSequentialParameterNumber(calculatedParameters, measurementNumber).stream()
                .collect(Collectors.toMap(CalculatedParameter::getParameterName, p -> p));
    }



    private List<CalculatedParameter> setSequentialParameterNumber(List<CalculatedParameter> calculatedParameters
                                                                 , Integer measurementNumber) {
        int sequentialNumber = 1;
        String square = measurementService.get(String.valueOf(MeasuredParameterType.SQUARE));
        String quantity = measurementService.get(String.valueOf(MeasuredParameterType.QUANTITY));
        int size = calculatedParameters.size();
        for (CalculatedParameter parameter : calculatedParameters) {
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
        return calculatedParameters;
    }

    public CalculatedParameter createQuantityParameter(int quantity) {
        return mapper.mapToQuantity(constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY))
                , constUnit.get(String.valueOf(UnitMeasurementType.PIECES))
                , quantity);
    }

    private void addQuantity(List<CalculatedParameter> calculatedParameters
                           , ParameterCalculationType calculation
                           , Integer quantity) {
        if (!calculation.equals(ParameterCalculationType.QUANTITY) && quantity != null && quantity > 1) {
            calculatedParameters.add(mapper.mapToQuantity(constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY))
                    , constUnit.get(String.valueOf(UnitMeasurementType.PIECES))
                    , quantity));
        }
    }
}