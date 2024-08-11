package ru.nabokovsg.diagnosedNK.mapper.equipmentType;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentType.EquipmentTypeDto;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentType.ResponseEquipmentTypeDto;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentType;

@Mapper(componentModel = "spring")
public interface EquipmentTypeMapper {

    EquipmentType mapToEquipmentType(EquipmentTypeDto equipmentTypeDto);

    ResponseEquipmentTypeDto mapResponseEquipmentTypeDto(EquipmentType equipmentType);
}