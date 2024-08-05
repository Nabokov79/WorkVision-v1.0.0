package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement.EquipmentElementDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentElement;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentPartElement;

import java.util.Set;

public interface EquipmentPartElementService {

    EquipmentPartElement save(EquipmentElement element, EquipmentElementDto elementDto);

    EquipmentPartElement update(Set<EquipmentPartElement> partsElement, EquipmentElementDto elementDto);
}