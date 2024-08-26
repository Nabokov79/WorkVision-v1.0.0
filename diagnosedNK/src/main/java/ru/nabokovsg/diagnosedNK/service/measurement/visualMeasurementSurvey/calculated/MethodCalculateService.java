package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

import java.util.List;
import java.util.Set;

public interface MethodCalculateService {

    List<CalculatedParameter> countMin(Set<ParameterMeasurement> measurements);

    List<CalculatedParameter> countMax(Set<ParameterMeasurement> measurements);

    List<CalculatedParameter> countMaxMin(Set<ParameterMeasurement> measurements);

    List<CalculatedParameter> countSquare(Set<ParameterMeasurement> measurements);

   Set<CalculatedParameter> countQuantity(Set<Integer> parameters);

    CalculatedParameter createQuantityParameter(int quantity);

    Integer getQuantity(Integer quantityDb, Integer quantityDto);
}