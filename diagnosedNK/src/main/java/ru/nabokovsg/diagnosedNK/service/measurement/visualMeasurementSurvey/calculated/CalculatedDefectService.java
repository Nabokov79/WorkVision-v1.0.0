package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.CalculationDefectOrRepair;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;

import java.util.Set;

public interface CalculatedDefectService {

    void save(Set<IdentifiedDefect> defects, IdentifiedDefect defect, CalculationDefectOrRepair calculation);
}