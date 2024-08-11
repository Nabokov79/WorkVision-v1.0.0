package ru.nabokovsg.diagnosedNK.mapper.equipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentElement.ResponseEquipmentElementDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentDiagnosed;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentTypeElement;

@Mapper(componentModel = "spring")
public interface EquipmentElementMapper {

    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(target = "partsElement", ignore = true)
    @Mapping(source = "equipmentDiagnosed", target = "equipmentDiagnosed")
    @Mapping(target = "standardSize", ignore = true)
    @Mapping(target = "id", ignore = true)
    EquipmentElement mapToElement(EquipmentTypeElement element, EquipmentDiagnosed equipmentDiagnosed);

    @Mapping(source = "element.id", target = "id")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(source = "element.standardSize", target = "standardSize")
    ResponseEquipmentElementDto mapToResponseEquipmentElementDto(EquipmentElement element);

    @Mapping(source = "standardSize", target = "standardSize")
    @Mapping(target = "id", ignore = true)
    EquipmentElement mapWithStandardSize(@MappingTarget EquipmentElement element, StandardSize standardSize);
}