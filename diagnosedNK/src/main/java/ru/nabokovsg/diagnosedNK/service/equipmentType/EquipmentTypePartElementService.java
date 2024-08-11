package ru.nabokovsg.diagnosedNK.service.equipmentType;

import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypePartElement.EquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypePartElement.ResponseEquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentTypePartElement;

import java.util.List;

public interface EquipmentTypePartElementService {

    ResponseEquipmentTypePartElementDto save(EquipmentTypePartElementDto partElementDto);

    ResponseEquipmentTypePartElementDto update(EquipmentTypePartElementDto partElementDto);

    List<ResponseEquipmentTypePartElementDto> getAll(Long id);

    EquipmentTypePartElement getById(Long id);
}