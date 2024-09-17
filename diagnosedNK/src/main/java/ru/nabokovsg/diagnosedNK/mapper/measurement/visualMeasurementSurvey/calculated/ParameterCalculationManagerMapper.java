package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

@Mapper(componentModel = "spring")
public interface ParameterCalculationManagerMapper {

    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameter.value", target = "minValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculatedParameter mapToMinValue(ParameterMeasurement parameter);

    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameter.value", target = "maxValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "minValue", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculatedParameter mapToMaxValue(ParameterMeasurement parameter);

    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameter.value", target = "minValue")
    @Mapping(source = "parameter.value", target = "maxValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculatedParameter mapToMaxMinValue(ParameterMeasurement parameter);

    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameter.value", target = "integerValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculatedParameter mapToQuantity(ParameterMeasurement parameter);

    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "parameterNumber", target = "parameterNumber")
    void mapWithSequenceNumber(@MappingTarget CalculatedParameter parameter
                                            , Integer measurementNumber
                                            , Integer parameterNumber);
}