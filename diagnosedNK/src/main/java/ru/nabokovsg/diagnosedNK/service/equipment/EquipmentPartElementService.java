package ru.nabokovsg.diagnosedNK.service.equipment;

import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentElement.EquipmentElementDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;

import java.util.Set;

public interface EquipmentPartElementService {

    void save(EquipmentElement element, EquipmentElementDto elementDto);

    EquipmentPartElement update(Set<EquipmentPartElement> partsElement, EquipmentElementDto elementDto);

    EquipmentPartElement get(Long id);
}