package ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentRepair;

import java.util.Set;

public interface EquipmentRepairRepository extends JpaRepository<EquipmentRepair, Long> {

    Set<EquipmentRepair> findAllByEquipmentDiagnosedId(Long equipmentId);
}