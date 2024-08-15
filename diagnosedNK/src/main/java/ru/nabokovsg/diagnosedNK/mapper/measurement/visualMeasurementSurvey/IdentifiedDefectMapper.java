package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.identifiedDefect.ResponseIdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface IdentifiedDefectMapper {

    @Mapping(source = "defectDto.equipmentId", target = "equipmentId")
    @Mapping(source = "defect.id", target = "defectId")
    @Mapping(source = "defect.defectName", target = "defectName")
    @Mapping(source = "defect.useCalculateThickness", target = "useCalculateThickness")
    @Mapping(source = "defect.notMeetRequirements", target = "notMeetRequirements")
    @Mapping(source = "defectDto.id", target = "id")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(target = "parameterMeasurements", ignore = true)
    IdentifiedDefect mapToIdentifiedDefect(IdentifiedDefectDto defectDto
                                         , Defect defect
                                         , EquipmentElement element);

    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    IdentifiedDefect mapWithEquipmentPartElement(@MappingTarget IdentifiedDefect defect
                                                              , EquipmentPartElement partElement);

    @Mapping(source = "parameters", target = "parameterMeasurements")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    IdentifiedDefect mapWithParameterMeasurements(@MappingTarget IdentifiedDefect defect
                                                               , Set<ParameterMeasurement> parameters);

    ResponseIdentifiedDefectDto mapToResponseIdentifiedDefectDto(IdentifiedDefect defect);
}