package ru.nabokovsg.diagnosedNK.service.diagnosticEquipmentData;

import ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData.ElementDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;

import java.util.List;

public interface EquipmentStandardSizeService {

    void save(DiagnosticEquipmentData diagnosticObjectData, List<ElementDto> elements);

    void update(DiagnosticEquipmentData diagnosticObjectData, List<ElementDto> elements);

    ElementData getByElementId(Long elementId);

    ElementData getByPartElementId(Long partElementId);
}