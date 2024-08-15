package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CalculatedElement;

@Mapper(componentModel = "spring")
public interface ExaminedPartElementMapper {

    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "id", ignore = true)
    CalculatedElement mapToExaminedPartElement(EquipmentPartElement partElement);

    @Mapping(source = "inspectionDto.inspection", target = "inspection")
    @Mapping(target = "id", ignore = true)
    CalculatedElement mapWithInspection(@MappingTarget CalculatedElement partElement, InspectionDto inspectionDto);
}