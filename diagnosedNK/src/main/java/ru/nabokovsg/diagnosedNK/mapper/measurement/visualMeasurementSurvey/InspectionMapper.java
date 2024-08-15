package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.ResponseInspectionDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CalculatedElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CalculatedVMSurvey;

@Mapper(componentModel = "spring")
public interface InspectionMapper {

    @Mapping(source = "inspectionDto.elementId", target = "elementId")
    @Mapping(source = "inspectionDto.partElementId", target = "partElementId")
    @Mapping(source = "inspection", target = "inspection")
    ResponseInspectionDto mapToResponseInspectionDto(InspectionDto inspectionDto, String inspection);

    @Mapping(source = "visualMeasuringSurvey.elementId", target = "elementId")
    @Mapping(target = "partElementId", ignore = true)
    @Mapping(source = "visualMeasuringSurvey.inspection", target = "inspection")
    ResponseInspectionDto mapToResponseInspectionDto(CalculatedVMSurvey visualMeasuringSurvey);

    @Mapping(source = "visualMeasuringSurvey.elementId", target = "elementId")
    @Mapping(source = "partElement.partElementId", target = "partElementId")
    @Mapping(source = "partElement.inspection", target = "inspection")
    ResponseInspectionDto mapToResponseInspectionDto(CalculatedVMSurvey visualMeasuringSurvey
                                                   , CalculatedElement partElement);
}