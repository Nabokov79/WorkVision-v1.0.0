package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;

@Mapper(componentModel = "spring")
public interface CalculatedParameterMapper {

    @Mapping(source = "defect", target = "defect")
    @Mapping(source = "parameterDb.id", target = "id")
    @Mapping(target = "minValue", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    CalculatedParameter mapWithDefect(@MappingTarget CalculatedParameter parameter
                                                   , CalculatedParameter parameterDb
                                                   , CalculatedDefect defect);

    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "parameterNumber", target = "parameterNumber")
    void mapWithSequenceNumber(@MappingTarget CalculatedParameter parameter
                                            , Integer measurementNumber
                                            , Integer parameterNumber);
}