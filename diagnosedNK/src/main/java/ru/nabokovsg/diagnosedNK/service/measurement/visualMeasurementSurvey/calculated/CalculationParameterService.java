package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.*;

public interface CalculationParameterService {

    List<CalculatedParameter> calculation(Set<ParameterMeasurement> parameterMeasurements
                                        , ParameterCalculationType calculation
                                        , int measurementNumber
                                        , Integer quantity
                                        , List<Integer> quantityParameters);

}