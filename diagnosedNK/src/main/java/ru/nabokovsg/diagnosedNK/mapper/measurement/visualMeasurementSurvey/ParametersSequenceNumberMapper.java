package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

@Mapper(componentModel = "spring")
public interface ParametersSequenceNumberMapper {

    @Mapping(target = "parameterName", ignore = true)
    ParameterMeasurement map(@MappingTarget ParameterMeasurement parameter, Integer measurementNumber, Integer parameterNumber);
}