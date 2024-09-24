package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.builders.ParameterMeasurementBuilder;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

import java.util.List;

public interface ParameterMeasurementFactory {

    List<ParameterMeasurement> get(ParameterMeasurementBuilder builder);
}