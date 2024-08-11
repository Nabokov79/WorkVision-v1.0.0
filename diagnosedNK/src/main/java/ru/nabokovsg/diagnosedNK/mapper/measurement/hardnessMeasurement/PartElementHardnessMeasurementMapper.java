package ru.nabokovsg.diagnosedNK.mapper.measurement.hardnessMeasurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.ElementHardnessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.HardnessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.PartElementHardnessMeasurement;

@Mapper(componentModel = "spring")
public interface PartElementHardnessMeasurementMapper {

    @Mapping(source = "partElement.id", target = "partElementId")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    @Mapping(source = "element", target = "elementMeasurement")
    @Mapping(source = "measurement", target = "measurement")
    @Mapping(target = "id", ignore = true)
    PartElementHardnessMeasurement mapToPartElementHardnessMeasurement(ElementHardnessMeasurement element
                                                                     , EquipmentPartElement partElement
                                                                     , HardnessMeasurement measurement);

    @Mapping(source = "measurement", target = "measurement")
    @Mapping(target = "id", ignore = true)
    PartElementHardnessMeasurement mapWithUltrasonicThicknessMeasurement(
                                                    @MappingTarget PartElementHardnessMeasurement partElementMeasurement
                                                                 , HardnessMeasurement measurement);
}