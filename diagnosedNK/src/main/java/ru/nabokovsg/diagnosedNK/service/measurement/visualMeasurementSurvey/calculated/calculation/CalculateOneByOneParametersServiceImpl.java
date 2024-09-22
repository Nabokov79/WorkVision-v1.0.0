package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameterType;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CalculateOneByOneParametersServiceImpl implements CalculateOneByOneParametersService {

    private final CalculateMeasurementVMSService measurementVMSService;
    private final SequentialParameterNumberService sequentialParameterNumberService;

    @Override
    public void calculateOneByOne(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        switch (parameterData.getTypeData()) {
            case DEFECT -> calculateIdentifiedDefectOneByOneParameters(parameterData, parameters);
            case REPAIR -> calculateCompletedRepairOneByOneParameters(parameterData, parameters);
            default -> throw new NotFoundException(String.format("Calculation data type=%s not supported"
                    , parameterData.getCalculationType()));
        }
    }

    private void calculateIdentifiedDefectOneByOneParameters(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        int measurementNumber = 1;
        for (IdentifiedDefect defect : parameterData.getDefects()) {
            compareParameters(parameters, measurementVMSService.calculationByCalculationType(defect.getParameterMeasurements()
                    , parameterData.getCalculationType()));
            sequentialParameterNumberService.setSequentialParameterNumber(parameters, measurementNumber);
            measurementNumber++;
        }
    }

    private void calculateCompletedRepairOneByOneParameters(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters) {
        int measurementNumber = 1;
        for (CompletedRepair repair : parameterData.getRepairs()) {
            compareParameters(parameters, measurementVMSService.calculationByCalculationType(repair.getParameterMeasurements()
                    , parameterData.getCalculationType()));
            sequentialParameterNumberService.setSequentialParameterNumber(parameters, measurementNumber);
            measurementNumber++;
        }
    }

    private void compareParameters(Map<String, CalculatedParameter> parameters, Map<String, CalculatedParameter> calculatedParameters) {
        if (parameters.isEmpty()) {
            parameters.putAll(calculatedParameters);
            return;
        }
        String quantityName = MeasuredParameterType.valueOf("QUANTITY").label;
        Map<Integer, Integer> coincidences = calculationNumberOfMatches(calculatedParameters, parameters);
        parameters.forEach((k, v) -> {
            if (quantityName.equals(v.getParameterName()) && coincidences.get(v.getMeasurementNumber()) == parameters.size()) {
                CalculatedParameter calculatedParameter = calculatedParameters.get(quantityName);
                CalculatedParameter parameter = parameters.get(quantityName);
                calculatedParameter.setIntegerValue(calculatedParameter.getIntegerValue() + parameter.getIntegerValue());
                calculatedParameters.put(quantityName, calculatedParameter);
            }
        });
    }

    private Map<Integer, Integer> calculationNumberOfMatches(Map<String, CalculatedParameter> parameters
            , Map<String, CalculatedParameter> calculatedParameters) {
        Map<Integer, Integer> coincidences = new HashMap<>();
        boolean coincidence = true;
        for (CalculatedParameter parameter : parameters.values()) {
            CalculatedParameter measurement = calculatedParameters.get(parameter.getParameterName());
            if (!parameter.getParameterName().equals(MeasuredParameterType.valueOf("QUANTITY").label)) {
                coincidence = Objects.equals(parameter.getMinValue(), measurement.getMinValue());
            }
            if (coincidence) {
                coincidences.merge(parameter.getMeasurementNumber(), 1, Integer::sum);
            }
        }
        return coincidences;
    }
}