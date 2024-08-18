package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;

import java.util.Set;

public interface CompletedRepairRepository extends JpaRepository<CompletedRepair, Long> {

    Set<CompletedRepair> findAllByEquipmentId(Long equipmentId);
}