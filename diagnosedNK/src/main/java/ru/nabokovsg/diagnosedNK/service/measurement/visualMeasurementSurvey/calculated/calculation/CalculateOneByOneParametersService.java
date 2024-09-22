package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;

import java.util.Map;

public interface CalculateOneByOneParametersService {

    void calculateOneByOne(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters);
}