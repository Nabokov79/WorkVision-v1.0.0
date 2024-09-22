package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;

import java.util.Map;

public interface SequentialParameterNumberService {

    void setSequentialParameterNumber(Map<String, CalculatedParameter> parameters, Integer measurementNumber);
}