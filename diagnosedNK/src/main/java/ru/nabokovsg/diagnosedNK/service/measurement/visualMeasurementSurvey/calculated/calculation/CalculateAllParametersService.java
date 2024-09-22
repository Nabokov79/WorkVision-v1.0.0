package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameterData;

import java.util.*;

public interface CalculateAllParametersService {

    void calculateAll(CalculatedParameterData parameterData, Map<String, CalculatedParameter> parameters);
}