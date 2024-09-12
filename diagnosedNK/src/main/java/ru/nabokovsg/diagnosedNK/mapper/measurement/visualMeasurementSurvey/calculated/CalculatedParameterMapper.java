package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;

@Mapper(componentModel = "spring")
public interface CalculatedParameterMapper {

    @Mapping(source = "defect", target = "defect")
    @Mapping(source = "parameter.minValue", target = "minValue")
    @Mapping(source = "parameter.maxValue", target = "maxValue")
    @Mapping(source = "parameter.integerValue", target = "integerValue")
    @Mapping(target = "id", ignore = true)
    CalculatedParameter mapWithDefect(@MappingTarget CalculatedParameter parameterDb
                                                   , CalculatedParameter parameter
                                                   , CalculatedDefect defect);

    @Mapping(source = "repair", target = "repair")
    @Mapping(source = "parameter.minValue", target = "minValue")
    @Mapping(source = "parameter.maxValue", target = "maxValue")
    @Mapping(source = "parameter.integerValue", target = "integerValue")
    @Mapping(target = "id", ignore = true)
    CalculatedParameter mapWithRepair(@MappingTarget CalculatedParameter parameterDb
            , CalculatedParameter parameter
            , CalculatedRepair repair);

    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "parameterNumber", target = "parameterNumber")
    void mapWithSequenceNumber(@MappingTarget CalculatedParameter parameter
                                            , Integer measurementNumber
                                            , Integer parameterNumber);
}