package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculateParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

@Mapper(componentModel = "spring")
public interface CalculateParameterMeasurementFactoryMapper {

    @Mapping(source = "defect", target = "defect")
    @Mapping(target = "id", ignore = true)
    CalculateParameterMeasurement mapWithDefect(@MappingTarget CalculateParameterMeasurement parameter, CalculatedDefect defect);

    @Mapping(source = "repair", target = "repair")
    @Mapping(target = "id", ignore = true)
    CalculateParameterMeasurement mapWithRepair(@MappingTarget CalculateParameterMeasurement parameter, CalculatedRepair repair);

    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "parameterNumber", target = "parameterNumber")
    void mapWithSequenceNumber(@MappingTarget CalculateParameterMeasurement parameter
                                            , Integer measurementNumber
                                            , Integer parameterNumber);

    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameter.value", target = "minValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculateParameterMeasurement mapToMinValue(ParameterMeasurement parameter);

    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameter.value", target = "maxValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "minValue", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculateParameterMeasurement mapToMaxValue(ParameterMeasurement parameter);

    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameter.value", target = "minValue")
    @Mapping(source = "parameter.value", target = "maxValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculateParameterMeasurement mapToMaxMinValue(ParameterMeasurement parameter);

    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameter.value", target = "integerValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculateParameterMeasurement mapToQuantity(ParameterMeasurement parameter);
}