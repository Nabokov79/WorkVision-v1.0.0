package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.RemarkByEquipment;

import java.util.Set;

public interface RemarkByEquipmentRepository extends JpaRepository<RemarkByEquipment, Long> {

    Set<RemarkByEquipment> findAllByEquipmentId(Long equipmentId);
}