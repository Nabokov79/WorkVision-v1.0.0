package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementControl.VisualMeasurementControl;

import java.util.Set;

public interface VisualMeasurementControlRepository extends JpaRepository<VisualMeasurementControl, Long> {

    Set<VisualMeasurementControl> findAllByWorkJournalId(Long workJournalId);
}