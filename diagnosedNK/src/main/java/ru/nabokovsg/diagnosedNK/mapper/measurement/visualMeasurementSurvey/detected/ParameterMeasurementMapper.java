package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;

@Mapper(componentModel = "spring")
public interface ParameterMeasurementMapper {

    @Mapping(source = "parameter.id", target = "parameterId")
    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameterDto.value", target = "value")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(target = "id", ignore = true)
    ParameterMeasurement mapToParameterMeasurement(MeasuredParameter parameter, ParameterMeasurementDto parameterDto);
}