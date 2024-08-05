package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.VisualMeasuringSurvey;

import java.util.Set;

public interface VisualMeasuringSurveyRepository extends JpaRepository<VisualMeasuringSurvey, Long> {

    VisualMeasuringSurvey findByEquipmentIdAndElementId(Long equipmentId, Long elementId);

    Set<VisualMeasuringSurvey> findAllByEquipmentId(Long equipmentId);
}