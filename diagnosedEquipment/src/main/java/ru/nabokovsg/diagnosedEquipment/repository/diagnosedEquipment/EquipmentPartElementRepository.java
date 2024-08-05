package ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentPartElement;

public interface EquipmentPartElementRepository extends JpaRepository<EquipmentPartElement, Long> {
}