package ru.nabokovsg.diagnosedEquipment.mapper.equipmentType;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypeElement.EquipmentTypeElementDto;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypeElement.ResponseEquipmentTypeElementDto;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentType;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentTypeElement;

@Mapper(componentModel = "spring")
public interface EquipmentTypeElementMapper {

    @Mapping(source = "elementDto.elementName", target = "elementName")
    @Mapping(source = "equipmentType", target = "equipmentType")
    @Mapping(source = "elementDto.id", target = "id")
    EquipmentTypeElement mapToElement(EquipmentTypeElementDto elementDto, EquipmentType equipmentType);

    ResponseEquipmentTypeElementDto mapToResponseEquipmentTypeElementDto(EquipmentTypeElement element);
}