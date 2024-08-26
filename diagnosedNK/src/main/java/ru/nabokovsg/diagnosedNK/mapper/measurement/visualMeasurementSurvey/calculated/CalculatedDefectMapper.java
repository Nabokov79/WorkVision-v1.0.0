package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;

@Mapper(componentModel = "spring")
public interface CalculatedDefectMapper {

    @Mapping(source = "defect.defectId", target = "defectId")
    @Mapping(source = "defect.defectName", target = "defectName")
    @Mapping(target = "weldedJointNumber", ignore = true)
    @Mapping(source = "defect.notMeetRequirements", target = "notMeetRequirements")
    @Mapping(source = "element", target = "element")
    @Mapping(target = "parameters", ignore = true)
    @Mapping(target = "partElement", ignore = true)
    @Mapping(target = "id", ignore = true)
    CalculatedDefect mapToCalculatedDefect(IdentifiedDefect defect, CalculatedElement element);
}