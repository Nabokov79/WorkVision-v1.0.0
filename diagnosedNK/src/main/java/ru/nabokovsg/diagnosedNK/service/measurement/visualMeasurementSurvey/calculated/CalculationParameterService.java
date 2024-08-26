package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.*;

public interface CalculationParameterService {

    List<CalculatedParameter> calculateByDefect(Set<IdentifiedDefect> defects, ParameterCalculationType calculation);

     List<CalculatedParameter> calculateByRepair(Set<CompletedRepair> repairs, ParameterCalculationType calculation);

}