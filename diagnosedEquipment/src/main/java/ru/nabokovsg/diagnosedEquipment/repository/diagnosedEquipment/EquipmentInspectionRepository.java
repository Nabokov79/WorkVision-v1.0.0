package ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentInspection;

import java.util.Set;

public interface EquipmentInspectionRepository extends JpaRepository<EquipmentInspection, Long> {

    Set<EquipmentInspection> findAllByEquipmentDiagnosedId(Long equipmentDiagnosedId);
}