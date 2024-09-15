package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedParameter;

@Mapper(componentModel = "spring")
public interface CalculateMeasurementVMSMapper {

    @Mapping(source = "parameterName", target = "parameterName")
    @Mapping(source = "unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "minValue", target = "minValue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(target = "parameterNumber", ignore = true)
    @Mapping(target = "maxValue", ignore = true)
    @Mapping(target = "defect", ignore = true)
    @Mapping(target = "repair", ignore = true)
    CalculatedParameter mapToSquare(String parameterName, String unitMeasurement, Double minValue);
}