package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.ResponseInspectionDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ExaminedPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.VisualMeasuringSurvey;

@Mapper(componentModel = "spring")
public interface InspectionMapper {

    @Mapping(source = "inspectionDto.elementId", target = "elementId")
    @Mapping(source = "inspectionDto.partElementId", target = "partElementId")
    @Mapping(source = "inspection", target = "inspection")
    ResponseInspectionDto mapToResponseInspectionDto(InspectionDto inspectionDto, String inspection);

    @Mapping(source = "visualMeasuringSurvey.elementId", target = "elementId")
    @Mapping(target = "partElementId", ignore = true)
    @Mapping(source = "visualMeasuringSurvey.inspection", target = "inspection")
    ResponseInspectionDto mapToResponseInspectionDto(VisualMeasuringSurvey visualMeasuringSurvey);

    @Mapping(source = "visualMeasuringSurvey.elementId", target = "elementId")
    @Mapping(source = "partElement.partElementId", target = "partElementId")
    @Mapping(source = "partElement.inspection", target = "inspection")
    ResponseInspectionDto mapToResponseInspectionDto(VisualMeasuringSurvey visualMeasuringSurvey
                                                   , ExaminedPartElement partElement);
}