package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.CalculationDefectOrRepair;

import java.util.Set;

public interface CalculatedRepairService {

    void save(Set<CompletedRepair> repairs, CompletedRepair repair, CalculationDefectOrRepair calculation);
}