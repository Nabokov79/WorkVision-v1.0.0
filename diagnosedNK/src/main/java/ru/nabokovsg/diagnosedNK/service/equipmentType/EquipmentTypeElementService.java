package ru.nabokovsg.diagnosedNK.service.equipmentType;

import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypeElement.EquipmentTypeElementDto;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypeElement.ResponseEquipmentTypeElementDto;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentTypeElement;

import java.util.List;

public interface EquipmentTypeElementService {

    ResponseEquipmentTypeElementDto save(EquipmentTypeElementDto elementDto);

    ResponseEquipmentTypeElementDto update(EquipmentTypeElementDto elementDto);

    List<ResponseEquipmentTypeElementDto> getAll(Long id);

    EquipmentTypeElement getById(long id);
}