package ru.nabokovsg.diagnosedNK.service.measurement;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

import java.util.List;
import java.util.Set;

public interface CalculationMeasurementService {

    List<CalculatedParameter> countMin(Set<ParameterMeasurement> measurements);

    List<CalculatedParameter> countMax(Set<ParameterMeasurement> measurements);

    List<CalculatedParameter> countMaxMin(Set<ParameterMeasurement> measurements);

    List<CalculatedParameter> countSquare(Set<ParameterMeasurement> measurements);

   Set<CalculatedParameter> countQuantityDefect(Set<IdentifiedDefect> defects);

    Set<CalculatedParameter> countQuantityRepair(Set<CompletedRepair> repairs);

    CalculatedParameter createQuantityParameter(int quantity);

    Integer getQuantity(Integer quantityDb, Integer quantityDto);
}