package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedPartElement;

@Mapper(componentModel = "spring")
public interface CalculatedPartElementMapper {

    @Mapping(source = "element", target = "element")
    @Mapping(source = "partElementName", target = "partElementName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inspection", ignore = true)
    CalculatedPartElement mapToCalculatedPartElement(CalculatedElement element, String partElementName);
}