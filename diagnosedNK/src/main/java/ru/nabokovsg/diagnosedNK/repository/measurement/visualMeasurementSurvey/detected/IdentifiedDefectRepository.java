package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;

import java.util.Set;

public interface IdentifiedDefectRepository extends JpaRepository<IdentifiedDefect, Long> {

    Set<IdentifiedDefect> findAllByEquipmentId(Long equipmentId);
    Set<IdentifiedDefect> findAllByEquipmentIdAndDefectId(Long equipmentId, Long defectId);
}