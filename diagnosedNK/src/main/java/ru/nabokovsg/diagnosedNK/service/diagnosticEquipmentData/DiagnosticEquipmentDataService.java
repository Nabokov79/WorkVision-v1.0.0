package ru.nabokovsg.diagnosedNK.service.diagnosticEquipmentData;

import ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData.EquipmentDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;

public interface DiagnosticEquipmentDataService {

    void save(EquipmentDto equipmentDto);

    DiagnosticEquipmentData get(Long equipmentId);
}