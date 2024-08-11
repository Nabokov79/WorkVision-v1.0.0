package ru.nabokovsg.diagnosedNK.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;

public interface EquipmentPartElementRepository extends JpaRepository<EquipmentPartElement, Long> {
}