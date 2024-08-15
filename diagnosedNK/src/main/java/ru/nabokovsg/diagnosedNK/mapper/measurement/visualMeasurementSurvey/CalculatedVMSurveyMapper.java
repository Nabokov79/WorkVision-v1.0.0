package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.visualMeasuringSurvey.ResponseCalculatedVMSurveyDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CalculatedVMSurvey;

@Mapper(componentModel = "spring")
public interface CalculatedVMSurveyMapper {

    @Mapping(source = "equipmentId", target = "equipmentId")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "id", ignore = true)
    CalculatedVMSurvey mapToVisualMeasuringSurvey(Long equipmentId, EquipmentElement element);

    @Mapping(source = "inspectionDto.inspection", target = "inspection")
    @Mapping(target = "id", ignore = true)
    CalculatedVMSurvey mapWithInspection(@MappingTarget CalculatedVMSurvey vmSurvey, InspectionDto inspectionDto);

    ResponseCalculatedVMSurveyDto mapToResponseVisualMeasuringSurveyDto(CalculatedVMSurvey vmSurvey);
}