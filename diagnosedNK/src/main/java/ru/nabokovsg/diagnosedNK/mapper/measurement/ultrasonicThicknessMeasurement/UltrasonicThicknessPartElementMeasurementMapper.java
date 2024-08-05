package ru.nabokovsg.diagnosedNK.mapper.measurement.ultrasonicThicknessMeasurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.ElementData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessPartElementMeasurement;

@Mapper(componentModel = "spring")
public interface UltrasonicThicknessPartElementMeasurementMapper {

    @Mapping(source = "objectElementData.partElementId", target = "partElementId")
    @Mapping(source = "objectElementData.partElementName", target = "partElementName")
    @Mapping(source = "element", target = "elementMeasurement")
    @Mapping(source = "measurement", target = "measurement")
    @Mapping(target = "id", ignore = true)
    UltrasonicThicknessPartElementMeasurement mapToUltrasonicThicknessPartElementMeasurement(
                                                                          UltrasonicThicknessElementMeasurement element
                                                                        , ElementData objectElementData
                                                                        , UltrasonicThicknessMeasurement measurement);


    @Mapping(source = "measurement", target = "measurement")
    @Mapping(target = "id", ignore = true)
    UltrasonicThicknessPartElementMeasurement mapWithUltrasonicThicknessMeasurement(
                                      @MappingTarget UltrasonicThicknessPartElementMeasurement partElementMeasurement
                                                   , UltrasonicThicknessMeasurement measurement);
}