package ru.nabokovsg.diagnosedNK.service.equipment;

import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentElement.EquipmentElementDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentElement.ResponseEquipmentElementDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;

import java.util.List;

public interface EquipmentElementService {

    ResponseEquipmentElementDto save(EquipmentElementDto elementDto);

    ResponseEquipmentElementDto update(EquipmentElementDto elementDto);

    EquipmentElement get(Long id);

    List<ResponseEquipmentElementDto> getAll(Long id);
}