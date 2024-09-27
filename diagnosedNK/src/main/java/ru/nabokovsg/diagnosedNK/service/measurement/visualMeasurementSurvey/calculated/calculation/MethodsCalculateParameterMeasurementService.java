package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.Map;
import java.util.Set;

public interface MethodsCalculateParameterMeasurementService {

    Map<String, CalculateParameterMeasurement> calculationByCalculationType(Set<CalculateParameterMeasurement> measurements, ParameterCalculationType calculationType);

    void countQuantity(Map<String, CalculateParameterMeasurement> calculatedParameters, Set<CalculateParameterMeasurement> measurements);
}