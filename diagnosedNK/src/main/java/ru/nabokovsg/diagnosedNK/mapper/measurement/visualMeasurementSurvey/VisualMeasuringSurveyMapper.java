package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.visualMeasuringSurvey.ResponseVisualMeasuringSurveyDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.VisualMeasuringSurvey;

@Mapper(componentModel = "spring")
public interface VisualMeasuringSurveyMapper {

    @Mapping(source = "equipmentId", target = "equipmentId")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "id", ignore = true)
    VisualMeasuringSurvey mapToVisualMeasuringSurvey(Long equipmentId, EquipmentElement element);

    @Mapping(source = "inspectionDto.inspection", target = "inspection")
    @Mapping(target = "id", ignore = true)
    VisualMeasuringSurvey mapWithInspection(@MappingTarget VisualMeasuringSurvey vmSurvey, InspectionDto inspectionDto);

    ResponseVisualMeasuringSurveyDto mapToResponseVisualMeasuringSurveyDto(VisualMeasuringSurvey vmSurvey);
}