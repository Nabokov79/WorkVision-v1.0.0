package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.Map;
import java.util.Set;

public interface CalculateMeasurementVMSService {

    Map<String, CalculatedParameter> calculationByCalculationType(Set<ParameterMeasurement> measurements, ParameterCalculationType calculation);

    void countQuantity(Map<String, CalculatedParameter> calculatedParameters, Set<ParameterMeasurement> measurements);
}