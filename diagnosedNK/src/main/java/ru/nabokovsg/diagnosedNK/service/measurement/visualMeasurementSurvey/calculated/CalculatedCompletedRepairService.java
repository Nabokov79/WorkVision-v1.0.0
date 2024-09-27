package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;

import java.util.Set;

public interface CalculatedCompletedRepairService {

    void save(Set<CompletedRepair> repairs, CompletedRepair repair, ElementRepair elementRepair);

    void update(Set<CompletedRepair> repairs, CompletedRepair repair, ElementRepair elementRepair);
}