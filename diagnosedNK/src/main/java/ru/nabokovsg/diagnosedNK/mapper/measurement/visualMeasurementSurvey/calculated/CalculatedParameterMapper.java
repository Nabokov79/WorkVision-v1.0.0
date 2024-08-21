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

    @Mapping(source = "parameterName", target = "parameterName")
    @Mapping(source = "unitMeasurement", target = "unitMeasurement")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "minValue", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculatedParameter maToEmptyParameter(String parameterName, String unitMeasurement);
}