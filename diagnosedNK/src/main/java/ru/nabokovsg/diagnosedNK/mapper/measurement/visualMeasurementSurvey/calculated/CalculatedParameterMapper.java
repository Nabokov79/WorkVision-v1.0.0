package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

@Mapper(componentModel = "spring")
public interface CalculatedParameterMapper {

    @Mapping(source = "defect", target = "defect")
    @Mapping(source = "parameterDb.id", target = "id")
    @Mapping(target = "minValue", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    CalculatedParameter mapWithDefect(@MappingTarget CalculatedParameter parameter
                                                   , CalculatedParameter parameterDb
                                                   , CalculatedDefect defect);

    @Mapping(source = "repair", target = "repair")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "minValue", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    CalculatedParameter mapWithRepair(@MappingTarget CalculatedParameter parameter, CalculatedRepair repair);

    @Mapping(source = "parameter.parameterName", target = "parameterName")
    @Mapping(source = "parameter.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "parameter.value", target = "minValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculatedParameter mapToCalculatedParameter(ParameterMeasurement parameter);

    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "parameterNumber", target = "parameterNumber")
    void mapWithSequenceNumber(@MappingTarget CalculatedParameter parameter
                                            , Integer measurementNumber
                                            , Integer parameterNumber);

    @Mapping(source = "parameterName", target = "parameterName")
    @Mapping(source = "unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "quantity", target = "minValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculatedParameter mapToQuantity(String parameterName, String unitMeasurement, Integer quantity);
}