package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

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

@Service
@RequiredArgsConstructor
public class CalculationParameterServiceImpl implements CalculationParameterService {

    private final CalculatedParameterMapper mapper;
    private final MethodCalculateService calculationService;
    private final ConstParameterMeasurementService measurementService;
    private final ConstParameterMeasurementService constParameter;
    private final ConstUnitMeasurementService constUnit;

    @Override
    public List<CalculatedParameter> calculation(Set<ParameterMeasurement> parameterMeasurements
                                               , ParameterCalculationType calculation
                                               , int measurementNumber
                                               , Integer quantity
                                               , List<Integer> quantityParameters) {
        List<CalculatedParameter> parameters = new ArrayList<>();
        switch (calculation) {
            case NO_ACTION ->
                parameters.addAll(parameterMeasurements.stream().map(mapper::mapToCalculatedParameter).toList());
            case SQUARE ->
                parameters.addAll(calculationService.countSquare(parameterMeasurements));
            case QUANTITY ->
                parameters.add(createQuantityParameter(calculationService.countQuantity(quantityParameters)));
            case MIN ->
                parameters.addAll(calculationService.countMin(parameterMeasurements));
            case MAX ->
                parameters.addAll(calculationService.countMax(parameterMeasurements));
            case MAX_MIN ->
                parameters.addAll(calculationService.countMaxMin(parameterMeasurements));
            default ->
                    throw new NotFoundException(String.format("Unknown type=%s calculation parameters", calculation));
        }
        addQuantity(parameters, calculation, quantity);
        return setSequentialParameterNumber(parameters, measurementNumber);
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

    private void addQuantity(List<CalculatedParameter> parameters
                           , ParameterCalculationType calculation
                           , Integer quantity) {
        if (!calculation.equals(ParameterCalculationType.QUANTITY) && quantity != null && quantity > 1) {
            parameters.add(mapper.mapToQuantity(constParameter.get(String.valueOf(MeasuredParameterType.QUANTITY))
                    , constUnit.get(String.valueOf(UnitMeasurementType.PIECES))
                    , quantity));
        }
    }
}