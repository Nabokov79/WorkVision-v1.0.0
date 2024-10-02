package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;

import java.util.Map;
import java.util.Set;

public interface MethodsCalculateParameterMeasurementService {

    void countMin(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements);

    void countMax(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements);

    void countMaxMin(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements);

    double countSquare(Set<CalculateParameterMeasurement> measurements);
}