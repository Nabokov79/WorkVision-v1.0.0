package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;

@Mapper(componentModel = "spring")
public interface CalculatedParameterMapper {

    @Mapping(source = "defect", target = "defect")
    void mapWithDefect(@MappingTarget CalculatedParameter parameter, CalculatedDefect defect);
}
