package ru.nabokovsg.diagnosedNK.repository.diagnosticEquipmentData;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;

public interface DiagnosticEquipmentDataRepository extends JpaRepository<DiagnosticEquipmentData, Long> {

    DiagnosticEquipmentData findByEquipmentId(Long equipmentId);
}