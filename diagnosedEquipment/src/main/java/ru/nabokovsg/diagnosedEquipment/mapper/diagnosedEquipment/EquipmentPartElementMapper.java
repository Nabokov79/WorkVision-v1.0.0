package ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentElement;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.StandardSize;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentTypePartElement;

@Mapper(componentModel = "spring")
public interface EquipmentPartElementMapper {

    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    @Mapping(source = "element", target = "element")
    @Mapping(source = "standardSize", target = "standardSize")
    @Mapping(target = "id", ignore = true)
    EquipmentPartElement mapToEquipmentTypePartElement(EquipmentElement element
                                                     , EquipmentTypePartElement partElement
                                                     , StandardSize standardSize);

    @Mapping(source = "standardSize", target = "standardSize")
    @Mapping(target = "id", ignore = true)
    EquipmentPartElement mapToUpdateEquipmentTypePartElement(@MappingTarget EquipmentPartElement partElement
                                                                          , StandardSize standardSize);
}