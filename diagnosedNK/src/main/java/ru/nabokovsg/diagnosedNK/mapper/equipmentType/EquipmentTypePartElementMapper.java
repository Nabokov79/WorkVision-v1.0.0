package ru.nabokovsg.diagnosedNK.mapper.equipmentType;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypePartElement.EquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypePartElement.ResponseEquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentTypeElement;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentTypePartElement;

@Mapper(componentModel = "spring")
public interface EquipmentTypePartElementMapper {

    @Mapping(source = "partElementDto.partElementName", target = "partElementName")
    @Mapping(source = "element", target = "element")
    @Mapping(source = "partElementDto.id", target = "id")
    EquipmentTypePartElement mapToEquipmentTypePartElement(EquipmentTypePartElementDto partElementDto
                                                         , EquipmentTypeElement element);

    ResponseEquipmentTypePartElementDto mapToResponseEquipmentTypePartElementDto(EquipmentTypePartElement partElement);
}