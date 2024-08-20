package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.visualMeasuringSurvey.ResponseCalculatedVMSurveyDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.VisualMeasurementControl;

@Mapper(componentModel = "spring")
public interface VisualMeasurementControlMapper {

    @Mapping(source = "equipmentId", target = "equipmentId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "id", ignore = true)
    VisualMeasurementControl mapToVisualMeasuringSurvey(Long equipmentId, EquipmentElement element);

    @Mapping(source = "inspectionDto.inspection", target = "inspection")
    @Mapping(target = "id", ignore = true)
    VisualMeasurementControl mapWithInspection(@MappingTarget VisualMeasurementControl vmSurvey, InspectionDto inspectionDto);

    ResponseCalculatedVMSurveyDto mapToResponseVisualMeasuringSurveyDto(VisualMeasurementControl vmSurvey);
}