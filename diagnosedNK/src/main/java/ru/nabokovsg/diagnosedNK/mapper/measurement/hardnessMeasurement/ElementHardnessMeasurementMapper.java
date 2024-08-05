package ru.nabokovsg.diagnosedNK.mapper.measurement.hardnessMeasurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.ResponseElementHardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.ElementHardnessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.HardnessMeasurement;

@Mapper(componentModel = "spring")
public interface ElementHardnessMeasurementMapper {

    @Mapping(source = "equipmentId", target = "equipmentId")
    @Mapping(source = "objectElementData.elementId", target = "elementId")
    @Mapping(source = "objectElementData.elementName", target = "elementName")
    @Mapping(target = "id", ignore = true)
    ElementHardnessMeasurement mapToElementHardnessMeasurement(Long workJournalId
                                                             , Long equipmentId
                                                             , ElementData objectElementData);

    @Mapping(source = "measurement", target = "measurement")
    @Mapping(target = "id", ignore = true)
    ElementHardnessMeasurement mapWithHardnessMeasurement(@MappingTarget ElementHardnessMeasurement elementMeasurement
                                                                       , HardnessMeasurement measurement);

    ResponseElementHardnessMeasurementDto mapToResponseElementHardnessMeasurementDto(ElementHardnessMeasurement element);


}