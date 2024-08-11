package ru.nabokovsg.diagnosedNK.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;

import java.util.Set;

public interface EquipmentElementRepository extends JpaRepository<EquipmentElement, Long> {

    Set<EquipmentElement> findByEquipmentDiagnosedId(Long equipmentDiagnosedId);
}