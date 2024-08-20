package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.ResponseInspectionDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.VisualMeasurementControl;

@Mapper(componentModel = "spring")
public interface InspectionMapper {

    @Mapping(source = "inspectionDto.partElementId", target = "partElementId")
    @Mapping(source = "inspection", target = "inspection")
    @Mapping(target = "elementName", ignore = true)
    ResponseInspectionDto mapToResponseInspectionDto(InspectionDto inspectionDto, String inspection);

    @Mapping(source = "calculatedVMSurvey.elementName", target = "elementName")
    @Mapping(target = "partElementId", ignore = true)
    @Mapping(source = "calculatedVMSurvey.inspection", target = "inspection")
    ResponseInspectionDto mapToResponseInspectionDto(VisualMeasurementControl calculatedVMSurvey);
}