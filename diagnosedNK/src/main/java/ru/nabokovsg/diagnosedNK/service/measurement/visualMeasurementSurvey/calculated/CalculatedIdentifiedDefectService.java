package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;

import java.util.Set;

public interface CalculatedIdentifiedDefectService {

    void save(Set<IdentifiedDefect> defects, IdentifiedDefect identifiedDefect, Defect defect);

    void update(Set<IdentifiedDefect> defects, IdentifiedDefect identifiedDefect, Defect defect);
}