package ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.GeodesicMeasurementsPointDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseGeodesicMeasurementsPointDto;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;

@Mapper(componentModel = "spring")
public interface GeodesicMeasurementsPointMapper {

    @Mapping(source = "measurementDto.equipmentId", target = "equipmentId")
    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "measurementDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "measurementDto.numberMeasurementLocation", target = "numberMeasurementLocation")
    @Mapping(source = "measurementDto.referencePointValue", target = "referencePointValue")
    @Mapping(source = "measurementDto.controlPointValue", target = "controlPointValue")
    @Mapping(source = "measurementDto.transitionValue", target = "transitionValue")
    @Mapping(source = "measurementDto.id", target = "id")
    GeodesicMeasurementsPoint mapToGeodesicMeasurementsPoint(GeodesicMeasurementsPointDto measurementDto
                                                           , Integer measurementNumber);

    ResponseGeodesicMeasurementsPointDto mapToResponseGeodesicMeasurementsPointDto(
                                                                        GeodesicMeasurementsPoint geodesicMeasurement);
}