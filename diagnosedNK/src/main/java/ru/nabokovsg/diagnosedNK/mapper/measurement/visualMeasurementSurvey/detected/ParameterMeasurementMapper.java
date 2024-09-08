package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementControl.VisualMeasurementControl;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.norms.MeasuredParameter;

@Mapper(componentModel = "spring")
public interface ParameterMeasurementMapper {

    @Mapping(source = "parameter.id", target = "parameterId")
    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameterDto.value", target = "value")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameterDto.id", target = "id")
    @Mapping(target = "identifiedDefect", ignore = true)
    @Mapping(target = "completedRepair", ignore = true)
    ParameterMeasurement mapToParameterMeasurement(ParameterMeasurementDto parameterDto, MeasuredParameter parameter);

    @Mapping(source = "defect", target = "identifiedDefect")
    @Mapping(target = "id", ignore = true)
    ParameterMeasurement mapWithIdentifiedDefect(@MappingTarget ParameterMeasurement parameter
                                                              , IdentifiedDefect defect);

    @Mapping(source = "repair", target = "completedRepair")
    @Mapping(target = "id", ignore = true)
    ParameterMeasurement mapWithCompletedRepair(@MappingTarget ParameterMeasurement parameter
                                               , CompletedRepair repair);

    @Mapping(source = "vmControl", target = "visualMeasurementControl")
    @Mapping(target = "id", ignore = true)
    ParameterMeasurement mapWithVisualMeasurementControl(@MappingTarget ParameterMeasurement parameter
                                                                      , VisualMeasurementControl vmControl);

    @Mapping(source = "parameterDto.value", target = "value")
    @Mapping(target = "parameterId", ignore = true)
    @Mapping(target = "parameterName", ignore = true)
    @Mapping(target = "unitMeasurement", ignore = true)
    @Mapping(target = "identifiedDefect", ignore = true)
    @Mapping(target = "completedRepair", ignore = true)
    @Mapping(target = "visualMeasurementControl", ignore = true)
    @Mapping(source = "parameterDto.id", target = "id")
    ParameterMeasurement mapToUpdateParameterMeasurement(@MappingTarget ParameterMeasurement parameter
                                                                      , ParameterMeasurementDto parameterDto);
}