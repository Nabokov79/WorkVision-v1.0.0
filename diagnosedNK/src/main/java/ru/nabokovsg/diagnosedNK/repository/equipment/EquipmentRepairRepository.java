package ru.nabokovsg.diagnosedNK.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentRepair;

import java.util.Set;

public interface EquipmentRepairRepository extends JpaRepository<EquipmentRepair, Long> {

    Set<EquipmentRepair> findAllByEquipmentDiagnosedId(Long equipmentId);
}