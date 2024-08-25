package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.norms.ParameterCalculationType;

import java.util.Set;

public interface CalculatedParameterService {

    void saveForDefect(Set<IdentifiedDefect> defects, CalculatedDefect defect, ParameterCalculationType calculation);

    void updateForDefect(Set<IdentifiedDefect> defects, CalculatedDefect defect, ParameterCalculationType calculation);

    void saveForRepair(Set<CompletedRepair> repairs, CalculatedRepair repair, ParameterCalculationType calculation);

    void updateForRepair(Set<CompletedRepair> repairs, CalculatedRepair repair, ParameterCalculationType calculation);
}