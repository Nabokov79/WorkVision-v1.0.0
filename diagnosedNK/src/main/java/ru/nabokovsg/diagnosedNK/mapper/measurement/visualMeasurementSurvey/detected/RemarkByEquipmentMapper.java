package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.remarkByEquipment.RemarkByEquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.remarkByEquipment.ResponseRemarkByEquipmentDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.RemarkByEquipment;

@Mapper(componentModel = "spring")
public interface RemarkByEquipmentMapper {

    @Mapping(source = "remarkDto.equipmentId", target = "equipmentId")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(source = "remarkDto.inspection", target = "inspection")
    @Mapping(target = "partElementId", ignore = true)
    @Mapping(target = "partElementName", ignore = true)
    @Mapping(source = "remarkDto.id", target = "id", ignore = true)
    RemarkByEquipment mapWithEquipmentElement(RemarkByEquipmentDto remarkDto, EquipmentElement element);

    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    @Mapping(target = "id", ignore = true)
    void mapWithEquipmentPartElement(@MappingTarget RemarkByEquipment remark, EquipmentPartElement partElement);

    @Mapping(source = "inspection", target = "inspection")
    @Mapping(target = "id", ignore = true)
    RemarkByEquipment mapToUpdateRemarkByEquipment(@MappingTarget RemarkByEquipment remark, String inspection);

    ResponseRemarkByEquipmentDto mapToResponseRemarkByEquipmentDto(RemarkByEquipment remark);
}