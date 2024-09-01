package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.calculation;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.*;

public interface CalculationParameterService {

    Map<String, CalculatedParameter> calculation(Set<ParameterMeasurement> parameters
                                        , ParameterCalculationType calculation
                                        , int measurementNumber
                                        , Integer quantity
                                        , List<Integer> quantityParameters);

}