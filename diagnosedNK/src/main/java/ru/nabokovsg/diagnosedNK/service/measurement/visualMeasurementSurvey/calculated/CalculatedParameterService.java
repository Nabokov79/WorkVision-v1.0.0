package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;


import java.util.Set;

public interface CalculatedParameterService {

    void save(Set<IdentifiedDefect> defects, ParameterCalculationType calculation);
}