package ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicThicknessMeasurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.ResponseUltrasonicThicknessElementMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;

@Mapper(componentModel = "spring")
public interface UltrasonicThicknessElementMeasurementMapper {

    @Mapping(source = "equipmentId", target = "equipmentId")
    @Mapping(source = "element.id", target = "elementId")
    @Mapping(source = "element.elementName", target = "elementName")
    @Mapping(target = "id", ignore = true)
    UltrasonicThicknessElementMeasurement mapToUltrasonicThicknessElementMeasurement(Long equipmentId
                                                                                   , EquipmentElement element);

    @Mapping(source = "measurement", target = "measurement")
    @Mapping(target = "id", ignore = true)
    UltrasonicThicknessElementMeasurement mapWithUltrasonicThicknessElementMeasurement(
                                                @MappingTarget UltrasonicThicknessElementMeasurement elementMeasurement
                                                             , UltrasonicThicknessMeasurement measurement);

    ResponseUltrasonicThicknessElementMeasurementDto mapToResponseUltrasonicThicknessMeasurementDto(
                                                                         UltrasonicThicknessElementMeasurement element);
}