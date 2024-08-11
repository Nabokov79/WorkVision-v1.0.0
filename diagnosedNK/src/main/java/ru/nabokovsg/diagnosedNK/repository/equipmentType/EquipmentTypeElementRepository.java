package ru.nabokovsg.diagnosedNK.repository.equipmentType;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentTypeElement;

import java.util.Set;

public interface EquipmentTypeElementRepository extends JpaRepository<EquipmentTypeElement, Long> {

    boolean existsByEquipmentTypeIdAndElementName(Long equipmentTypeId, String elementName);

    Set<EquipmentTypeElement> findAllByEquipmentTypeId(Long equipmentTypeId);
}