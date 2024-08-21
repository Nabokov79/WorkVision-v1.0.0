package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;

import java.util.Set;

public interface ParametersSequenceNumberService {

    Set<CalculatedParameter> set(Set<CalculatedParameter> parameters);
}