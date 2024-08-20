package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.VisualMeasurementControl;

import java.util.Set;

public interface VisualMeasurementControlRepository extends JpaRepository<VisualMeasurementControl, Long> {

    VisualMeasurementControl findByEquipmentIdAndElementId(Long equipmentId, Long elementId);

    Set<VisualMeasurementControl> findAllByEquipmentId(Long equipmentId);
}