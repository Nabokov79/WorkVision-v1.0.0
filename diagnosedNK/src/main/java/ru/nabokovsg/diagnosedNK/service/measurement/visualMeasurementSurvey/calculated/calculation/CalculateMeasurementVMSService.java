package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CalculateMeasurementVMSService {

    List<CalculatedParameter> calculation(Map<String, CalculatedParameter> calculatedParameters
                                        , Set<ParameterMeasurement> parameters
                                        , ParameterCalculationType calculation);
}