package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalculateAllParametersServiceImpl implements CalculateAllParametersService {

    private final MethodsCalculateParameterMeasurementService measurementVMSService;
    private final SequentialParameterNumberService sequentialParameterNumberService;

    @Override
    public void calculateAll(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT -> calculateIdentifiedDefectAllParameters(parameterData, parameters);
            case REPAIR -> calculateCompletedRepairAllParameter(parameterData, parameters);
            default -> throw new NotFoundException(String.format("Calculation data type=%s not supported"
                    , parameterData.getCalculationType()));
        }
        sequentialParameterNumberService.setSequentialParameterNumber(parameters, 1);
    }

    private void calculateIdentifiedDefectAllParameters(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        Set<ParameterMeasurement> calculatedParameters =
                parameterData.getDefects()
                        .stream()
                        .map(IdentifiedDefect::getParameterMeasurements)
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
        parameters.putAll(measurementVMSService.calculationByCalculationType(calculatedParameters, parameterData.getCalculationType()));
        measurementVMSService.countQuantity(parameters, calculatedParameters);
    }

    private void calculateCompletedRepairAllParameter(CalculatedParameterData parameterData, Map<String, CalculateParameterMeasurement> parameters) {
        Set<ParameterMeasurement> calculatedParameters = parameterData.getRepairs()
                .stream()
                .map(CompletedRepair::getParameterMeasurements)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        measurementVMSService.calculationByCalculationType(calculatedParameters, parameterData.getCalculationType());
        measurementVMSService.countQuantity(parameters, calculatedParameters);
    }
}