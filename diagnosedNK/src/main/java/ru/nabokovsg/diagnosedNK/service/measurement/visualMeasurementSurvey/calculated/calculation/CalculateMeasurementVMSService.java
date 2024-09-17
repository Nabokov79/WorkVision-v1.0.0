package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;

import java.util.Map;
import java.util.Set;

public interface CalculateMeasurementVMSService {

    void countMin(Map<String, CalculatedParameter> parameters, Set<CalculatedParameter> measurements);

    void countMax(Map<String, CalculatedParameter> parameters, Set<CalculatedParameter> measurements);

    void countMaxMin(Map<String, CalculatedParameter> parameters, Set<CalculatedParameter> measurements);

    void countSquare(Map<String, CalculatedParameter> calculatedParameters, Set<CalculatedParameter> measurements);
}