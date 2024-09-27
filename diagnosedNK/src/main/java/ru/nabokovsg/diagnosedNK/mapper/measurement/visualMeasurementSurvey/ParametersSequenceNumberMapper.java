package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;

@Mapper(componentModel = "spring")
public interface ParametersSequenceNumberMapper {

    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "parameterNumber", target = "parameterNumber")
    CalculateParameterMeasurement map(@MappingTarget CalculateParameterMeasurement parameter, Integer measurementNumber, Integer parameterNumber);
}