package ru.nabokovsg.diagnosedNK.repository.diagnosticEquipmentData;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;

import java.util.Optional;
import java.util.Set;

public interface EquipmentStandardSizeRepository extends JpaRepository<ElementData, Long> {

    Set<ElementData> findAllByEquipmentData(DiagnosticEquipmentData diagnosticObjectData);

    Optional<ElementData> findByElementId(Long elementId);

    Optional<ElementData> findByPartElementId(Long partElementId);
}