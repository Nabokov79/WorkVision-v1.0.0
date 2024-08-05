package ru.nabokovsg.diagnosedEquipment.mapper.equipmentType;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentType.EquipmentTypeDto;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentType.ResponseEquipmentTypeDto;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentType;

@Mapper(componentModel = "spring")
public interface EquipmentTypeMapper {

    EquipmentType mapToEquipmentType(EquipmentTypeDto equipmentTypeDto);

    ResponseEquipmentTypeDto mapResponseEquipmentTypeDto(EquipmentType equipmentType);
}