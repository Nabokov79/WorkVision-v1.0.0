package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ExaminedPartElement;

@Mapper(componentModel = "spring")
public interface ExaminedPartElementMapper {

    @Mapping(source = "objectElementData.partElementId", target = "partElementId")
    @Mapping(source = "objectElementData.partElementName", target = "partElementName")
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "id", ignore = true)
    ExaminedPartElement mapToExaminedPartElement(ElementData objectElementData);

    @Mapping(source = "inspectionDto.inspection", target = "inspection")
    @Mapping(target = "id", ignore = true)
    ExaminedPartElement mapWithInspection(@MappingTarget ExaminedPartElement partElement, InspectionDto inspectionDto);
}