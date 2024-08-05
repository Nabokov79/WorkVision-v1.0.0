package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.ResponseIdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ExaminedPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.VisualMeasuringSurvey;
import ru.nabokovsg.diagnosedNK.model.norms.Defect;

@Mapper(componentModel = "spring")
public interface IdentifiedDefectMapper {

    @Mapping(source = "defect.id", target = "defectId")
    @Mapping(source = "defect.defectName", target = "defectName")
    @Mapping(source = "defect.useCalculateThickness", target = "useCalculateThickness")
    @Mapping(source = "defect.notMeetRequirements", target = "notMeetRequirements")
    @Mapping(source = "defectDto.id", target = "id")
    @Mapping(target = "parameterMeasurements", ignore = true)
    IdentifiedDefect mapToIdentifiedDefect(IdentifiedDefectDto defectDto, Defect defect);

    ResponseIdentifiedDefectDto mapToResponseIdentifiedDefectDto(IdentifiedDefect defect);

    @Mapping(source = "visualMeasuringSurvey", target = "visualMeasuringSurvey")
    @Mapping(target = "id", ignore = true)
    IdentifiedDefect mapWithVisualMeasuringSurvey(@MappingTarget IdentifiedDefect defect
                                                               , VisualMeasuringSurvey visualMeasuringSurvey);

    @Mapping(source = "examinedPartElement", target = "examinedPartElement")
    @Mapping(target = "id", ignore = true)
    IdentifiedDefect mapWithExaminedPartElement(@MappingTarget IdentifiedDefect defect
                                                             , ExaminedPartElement examinedPartElement);
}