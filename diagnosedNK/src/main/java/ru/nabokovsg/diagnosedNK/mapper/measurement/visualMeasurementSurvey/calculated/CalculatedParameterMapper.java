package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;

@Mapper(componentModel = "spring")
public interface CalculatedParameterMapper {

    @Mapping(source = "parameter.minValue", target = "minValue")
    @Mapping(source = "parameter.maxValue", target = "maxValue")
    @Mapping(source = "parameter.integerValue", target = "integerValue")
    @Mapping(target = "parameterName", ignore = true)
    @Mapping(target = "id", ignore = true)
    CalculateParameterMeasurement mapToUpdateCalculatedParameter(@MappingTarget CalculateParameterMeasurement parameterDb, CalculateParameterMeasurement parameter);


    @Mapping(source = "defect", target = "defect")
    @Mapping(target = "id", ignore = true)
    CalculateParameterMeasurement mapWithDefect(@MappingTarget CalculateParameterMeasurement parameter, CalculatedDefect defect);

    @Mapping(source = "repair", target = "repair")
    @Mapping(target = "id", ignore = true)
    CalculateParameterMeasurement mapWithRepair(@MappingTarget CalculateParameterMeasurement parameter, CalculatedRepair repair);
}