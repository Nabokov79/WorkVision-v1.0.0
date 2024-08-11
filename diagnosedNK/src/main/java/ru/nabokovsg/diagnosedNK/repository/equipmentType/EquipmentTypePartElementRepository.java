package ru.nabokovsg.diagnosedNK.repository.equipmentType;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentTypePartElement;

import java.util.Set;

public interface EquipmentTypePartElementRepository extends JpaRepository<EquipmentTypePartElement, Long> {

    boolean existsByElementIdAndPartElementName(Long elementId, String partElementName);

    Set<EquipmentTypePartElement> findAllByElementId(Long elementId);
}