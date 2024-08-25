package ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.calculated;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;

@Mapper(componentModel = "spring")
public interface CalculatedElementMapper {

    @Mapping(source = "equipmentId", target = "equipmentId")
    @Mapping(source = "elementName", target = "elementName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "inspection", ignore = true)
    @Mapping(target = "defects", ignore = true)
    @Mapping(target = "repairs", ignore = true)
    @Mapping(target = "partElements", ignore = true)
    CalculatedElement mapToCalculatedElement(Long equipmentId, String elementName);
}