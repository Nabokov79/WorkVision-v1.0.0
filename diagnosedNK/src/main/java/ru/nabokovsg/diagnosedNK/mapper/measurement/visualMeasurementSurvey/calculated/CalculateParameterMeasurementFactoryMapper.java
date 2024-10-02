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

    @Mapping(source = "parameterName", target = "parameterName")
    @Mapping(source = "unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "minValue", target = "minValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculateParameterMeasurement mapToCalculateParameterMeasurement(String parameterName, String unitMeasurement, Double minValue);

}