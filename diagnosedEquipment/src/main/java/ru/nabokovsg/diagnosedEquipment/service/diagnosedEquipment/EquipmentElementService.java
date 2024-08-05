package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement.EquipmentElementDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement.ResponseEquipmentElementDto;

import java.util.List;

public interface EquipmentElementService {

    ResponseEquipmentElementDto save(EquipmentElementDto elementDto);

    ResponseEquipmentElementDto update( EquipmentElementDto elementDto);

    List<ResponseEquipmentElementDto> getAll(Long id);
}