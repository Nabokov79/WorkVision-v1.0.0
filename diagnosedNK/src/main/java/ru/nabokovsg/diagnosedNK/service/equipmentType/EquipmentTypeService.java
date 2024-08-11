package ru.nabokovsg.diagnosedNK.service.equipmentType;

import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentType.EquipmentTypeDto;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentType.ResponseEquipmentTypeDto;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentType;

import java.util.List;

public interface EquipmentTypeService {

    ResponseEquipmentTypeDto save(EquipmentTypeDto equipmentTypeDto);

    ResponseEquipmentTypeDto update(EquipmentTypeDto equipmentTypeDto);

   ResponseEquipmentTypeDto get(Long id);

    List<ResponseEquipmentTypeDto> getAll();

    void delete(Long id);

    EquipmentType getById(Long id);
}