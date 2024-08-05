package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ParameterMeasurement;

@Mapper(componentModel = "spring")
public interface ParametersSequenceNumberMapper {

    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "parameterNumber", target = "parameterNumber")
    ParameterMeasurement map(@MappingTarget ParameterMeasurement parameter, Integer measurementNumber, Integer parameterNumber);
}