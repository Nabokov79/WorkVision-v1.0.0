package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.parameterMeasurement.ParameterMeasurementDto;
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
    @Mapping(source = "defect", target = "identifiedDefect")
    @Mapping(target = "id", ignore = true)
    ParameterMeasurement mapWithIdentifiedDefect(MeasuredParameter parameter
                                               , ParameterMeasurementDto parameterDto
                                               , IdentifiedDefect defect);

    @Mapping(source = "parameter.id", target = "parameterId")
    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameterDto.value", target = "value")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "repair", target = "completedRepair")
    @Mapping(target = "id", ignore = true)
    ParameterMeasurement mapWithIdentifiedDefect(MeasuredParameter parameter
                                               , ParameterMeasurementDto parameterDto
                                               , CompletedRepair repair);

    @Mapping(source = "parameter.id", target = "parameterId")
    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameterDto.value", target = "value")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "vmControl", target = "visualMeasurementControl")
    @Mapping(target = "id", ignore = true)
    ParameterMeasurement mapWithVisualMeasurementControl(MeasuredParameter parameter
                                                       , ParameterMeasurementDto parameterDto
                                                       , VisualMeasurementControl vmControl);

    @Mapping(source = "parameterDto.value", target = "value")
    @Mapping(target = "parameterId", ignore = true)
    @Mapping(target = "parameterName", ignore = true)
    @Mapping(target = "unitMeasurement", ignore = true)
    @Mapping(target = "identifiedDefect", ignore = true)
    @Mapping(target = "completedRepair", ignore = true)
    @Mapping(target = "visualMeasurementControl", ignore = true)
    @Mapping(target = "id", ignore = true)
    ParameterMeasurement mapToUpdateParameterMeasurement(@MappingTarget ParameterMeasurement parameter
                                                                      , ParameterMeasurementDto parameterDto);
}