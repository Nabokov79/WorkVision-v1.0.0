package ru.nabokovsg.diagnosedEquipment.service.equipmentType;

import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypePartElement.EquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypePartElement.ResponseEquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentTypePartElement;

import java.util.List;

public interface EquipmentTypePartElementService {

    ResponseEquipmentTypePartElementDto save(EquipmentTypePartElementDto partElementDto);

    ResponseEquipmentTypePartElementDto update(EquipmentTypePartElementDto partElementDto);

    List<ResponseEquipmentTypePartElementDto> getAll(Long id);

    EquipmentTypePartElement getById(Long id);
}