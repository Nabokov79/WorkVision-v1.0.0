package ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentElement;

import java.util.Set;

public interface EquipmentElementRepository extends JpaRepository<EquipmentElement, Long> {

    Set<EquipmentElement> findByEquipmentDiagnosedId(Long equipmentDiagnosedId);
}