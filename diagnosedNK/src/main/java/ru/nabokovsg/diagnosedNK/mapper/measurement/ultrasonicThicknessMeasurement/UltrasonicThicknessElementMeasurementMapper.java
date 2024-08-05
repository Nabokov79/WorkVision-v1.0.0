package ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicThicknessMeasurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.ResponseUltrasonicThicknessElementMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;

@Mapper(componentModel = "spring")
public interface UltrasonicThicknessElementMeasurementMapper {

    @Mapping(source = "equipmentId", target = "equipmentId")
    @Mapping(source = "objectElementData.elementId", target = "elementId")
    @Mapping(source = "objectElementData.elementName", target = "elementName")
    @Mapping(target = "id", ignore = true)
    UltrasonicThicknessElementMeasurement mapToUltrasonicThicknessElementMeasurement(Long workJournalId
                                                                                , Long equipmentId
                                                                                , ElementData objectElementData);

    @Mapping(source = "measurement", target = "measurement")
    @Mapping(target = "id", ignore = true)
    UltrasonicThicknessElementMeasurement mapWithUltrasonicThicknessElementMeasurement(
                                                @MappingTarget UltrasonicThicknessElementMeasurement elementMeasurement
                                                             , UltrasonicThicknessMeasurement measurement);

    ResponseUltrasonicThicknessElementMeasurementDto mapToResponseUltrasonicThicknessMeasurementDto(
                                                                         UltrasonicThicknessElementMeasurement element);
}