package ru.nabokovsg.diagnosedNK.mapper.diagnosticEquipmentData;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData.ElementDto;
import ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData.PartElementDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;

@Mapper(componentModel = "spring")
public interface StandardSizeMapper {

    @Mapping(source = "element.elementId", target = "elementTypeId")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(source = "element.standardSize.designThickness", target = "designThickness")
    @Mapping(source = "element.standardSize.minDiameter", target = "minDiameter")
    @Mapping(source = "element.standardSize.maxDiameter", target = "maxDiameter")
    @Mapping(source = "equipmentData", target = "equipmentData")
    @Mapping(target = "partElementId", ignore = true)
    @Mapping(target = "id", ignore = true)
    ElementData mapWithElementDto(ElementDto element
                                      , DiagnosticEquipmentData equipmentData);

    @Mapping(source = "element.elementId", target = "elementTypeId")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(source = "partElement.partElementId", target = "partElementTypeId")
    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partName", target = "partElementName")
    @Mapping(source = "partElement.standardSize.designThickness", target = "designThickness")
    @Mapping(source = "partElement.standardSize.minDiameter", target = "minDiameter")
    @Mapping(source = "partElement.standardSize.maxDiameter", target = "maxDiameter")
    @Mapping(source = "equipmentData", target = "equipmentData")
    @Mapping(target = "id", ignore = true)
    ElementData mapWithPartElementDto(ElementDto element
                                          , PartElementDto partElement
                                          , DiagnosticEquipmentData equipmentData);

    @Mapping(source = "element.elementId", target = "elementTypeId")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(source = "element.standardSize.designThickness", target = "designThickness")
    @Mapping(source = "element.standardSize.minDiameter", target = "minDiameter")
    @Mapping(source = "element.standardSize.maxDiameter", target = "maxDiameter")
    @Mapping(source = "equipmentData", target = "equipmentData")
    @Mapping(target = "partElementId", ignore = true)
    @Mapping(source = "id", target = "id")
    ElementData mapUpdateWithElementDto(Long id
                                            , ElementDto element
                                            , DiagnosticEquipmentData equipmentData);

    @Mapping(source = "element.elementId", target = "elementTypeId")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(source = "partElement.partElementId", target = "partElementTypeId")
    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partName", target = "partElementName")
    @Mapping(source = "partElement.standardSize.designThickness", target = "designThickness")
    @Mapping(source = "partElement.standardSize.minDiameter", target = "minDiameter")
    @Mapping(source = "partElement.standardSize.maxDiameter", target = "maxDiameter")
    @Mapping(source = "equipmentData", target = "equipmentData")
    @Mapping(source = "id", target = "id")
    ElementData mapUpdateWithPartElementDto(Long id
                                                , ElementDto element
                                                , PartElementDto partElement
                                                , DiagnosticEquipmentData equipmentData);
}