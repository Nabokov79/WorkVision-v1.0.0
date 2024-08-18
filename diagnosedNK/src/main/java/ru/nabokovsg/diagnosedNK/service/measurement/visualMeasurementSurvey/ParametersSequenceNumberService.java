package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

import java.util.Set;

public interface ParametersSequenceNumberService {

    Set<ParameterMeasurement> set(Set<ParameterMeasurement> parameters);
}