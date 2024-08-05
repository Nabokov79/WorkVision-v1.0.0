package ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseEquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;

@Mapper(componentModel = "spring")
public interface EquipmentGeodesicMeasurementsMapper {

    @Mapping(source = "equipmentId", target = "equipmentId")
    @Mapping(target = "referencePoints", ignore = true)
    @Mapping(target = "controlPoints", ignore = true)
    @Mapping(target = "id", ignore = true)
    EquipmentGeodesicMeasurements mapToEquipmentGeodesicMeasurements(Long equipmentId);

    ResponseEquipmentGeodesicMeasurementsDto mapToResponseEquipmentGeodesicMeasurementsDto(
                                                                            EquipmentGeodesicMeasurements measurements);
}