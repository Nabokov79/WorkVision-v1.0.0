package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;

import java.util.Map;

public interface SequentialParameterNumberService {

    void setSequentialParameterNumber(Map<String, CalculateParameterMeasurement> parameters, Integer measurementNumber);
}