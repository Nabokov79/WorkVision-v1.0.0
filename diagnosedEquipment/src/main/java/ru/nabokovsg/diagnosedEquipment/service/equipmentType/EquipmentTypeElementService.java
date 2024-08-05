package ru.nabokovsg.diagnosedEquipment.service.equipmentType;

import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypeElement.EquipmentTypeElementDto;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypeElement.ResponseEquipmentTypeElementDto;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentTypeElement;

import java.util.List;

public interface EquipmentTypeElementService {

    ResponseEquipmentTypeElementDto save(EquipmentTypeElementDto elementDto);

    ResponseEquipmentTypeElementDto update(EquipmentTypeElementDto elementDto);

    List<ResponseEquipmentTypeElementDto> getAll(Long id);

    EquipmentTypeElement getById(long id);
}