package ru.nabokovsg.diagnosedNK.mapper.equipmentType;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypeElement.EquipmentTypeElementDto;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypeElement.ResponseEquipmentTypeElementDto;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentType;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentTypeElement;

@Mapper(componentModel = "spring")
public interface EquipmentTypeElementMapper {

    @Mapping(source = "elementDto.elementName", target = "elementName")
    @Mapping(source = "equipmentType", target = "equipmentType")
    @Mapping(source = "elementDto.id", target = "id")
    EquipmentTypeElement mapToElement(EquipmentTypeElementDto elementDto, EquipmentType equipmentType);

    ResponseEquipmentTypeElementDto mapToResponseEquipmentTypeElementDto(EquipmentTypeElement element);
}