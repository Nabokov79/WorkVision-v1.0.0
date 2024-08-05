package ru.nabokovsg.diagnosedEquipment.repository.equipmentType;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentTypeElement;

import java.util.Set;

public interface EquipmentTypeElementRepository extends JpaRepository<EquipmentTypeElement, Long> {

    boolean existsByEquipmentTypeIdAndElementName(Long equipmentTypeId, String elementName);

    Set<EquipmentTypeElement> findAllByEquipmentTypeId(Long equipmentTypeId);
}