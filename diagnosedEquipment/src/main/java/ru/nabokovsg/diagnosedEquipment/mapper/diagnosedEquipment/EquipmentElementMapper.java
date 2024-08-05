package ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement.ResponseEquipmentElementDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentDiagnosed;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentElement;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.StandardSize;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentTypeElement;

@Mapper(componentModel = "spring")
public interface EquipmentElementMapper {

    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
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