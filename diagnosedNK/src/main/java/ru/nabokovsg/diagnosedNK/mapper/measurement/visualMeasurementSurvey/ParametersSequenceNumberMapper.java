package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;

@Mapper(componentModel = "spring")
public interface ParametersSequenceNumberMapper {

    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "parameterNumber", target = "parameterNumber")
    CalculatedParameter map(@MappingTarget CalculatedParameter parameter, Integer measurementNumber, Integer parameterNumber);
}