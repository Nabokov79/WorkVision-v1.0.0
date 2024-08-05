package ru.nabokovsg.diagnosedEquipment.service.equipmentType;

import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentType.EquipmentTypeDto;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentType.ResponseEquipmentTypeDto;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentType;

import java.util.List;

public interface EquipmentTypeService {

    ResponseEquipmentTypeDto save(EquipmentTypeDto equipmentTypeDto);

    ResponseEquipmentTypeDto update(EquipmentTypeDto equipmentTypeDto);

   ResponseEquipmentTypeDto get(Long id);

    List<ResponseEquipmentTypeDto> getAll();

    void delete(Long id);

    EquipmentType getById(Long id);
}