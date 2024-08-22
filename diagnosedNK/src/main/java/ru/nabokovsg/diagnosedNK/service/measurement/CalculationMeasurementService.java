package ru.nabokovsg.diagnosedNK.service.measurement;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

import java.util.Map;
import java.util.Set;

public interface CalculationMeasurementService {

    CalculatedParameter countMin(CalculatedParameter parameterMeasurement, ParameterMeasurement parameter);

    CalculatedParameter countMax(CalculatedParameter parameterMeasurement, ParameterMeasurement parameter);

    Set<CalculatedParameter> countSquare(Map<Long, CalculatedParameter> parameterMeasurements
                                        , Map<String, ParameterMeasurement> parameters);

   Set<CalculatedParameter> countQuantity(Map<Long, CalculatedParameter> parameterMeasurements
                                         , Map<String,ParameterMeasurement> parameters);

    Integer getQuantity(Integer quantityDb, Integer quantityDto);
}