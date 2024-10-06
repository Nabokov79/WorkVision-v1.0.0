package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.model.measurement.enums.TypeVMSData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateAllParametersServiceImpl implements CalculateAllParametersService {

    private final MethodsCalculateParameterMeasurementService measurementVMSService;
    private final SequentialParameterNumberService sequentialParameterNumberService;

    @Override
    public void calculateAll(TypeVMSData typeData, Set<ParameterMeasurement> calculatedParameters, Map<String, CalculateParameterMeasurement> parameters) {
        switch (typeData) {
            case DEFECT -> calculateIdentifiedDefectAllParameters(parameterData, parameters);
            case REPAIR -> calculateCompletedRepairAllParameter(parameterData, parameters);
            default -> throw new NotFoundException(String.format("Calculation data type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        sequentialParameterNumberService.setSequentialParameterNumber(parameters, 1);
    }

    @Override
    public void calculateAllParameters(Set<CalculateParameterMeasurement> calculatedParameters, ParameterCalculationType calculationType, Map<String, CalculateParameterMeasurement> parameters) {
        parameters.putAll(measurementVMSService.calculationByCalculationType(calculatedParameters, calculationType));
        countQuantity(parameters, calculatedParameters);
    }

    @Override
    public void calculateCompletedRepairAllParameter(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        Set<ParameterMeasurement> calculatedParameters = parameterData.getRepairs()
                .stream()
                .map(CompletedRepair::getParameterMeasurements)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        measurementVMSService.calculationByCalculationType(calculatedParameters, parameterData.getCalculationType());
        countQuantity(parameters, calculatedParameters);
    }

    private void countQuantity(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements) {
        String parameterName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<String, CalculateParameterMeasurement> calculatedParameter = new HashMap<>(1);
        measurements.forEach(v -> {
            if (v.getParameterName().equals(parameterName)) {
                CalculateParameterMeasurement parameter = calculatedParameter.get(parameterName);
                if (parameter == null) {
                    calculatedParameter.put(parameterName, v);
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
}