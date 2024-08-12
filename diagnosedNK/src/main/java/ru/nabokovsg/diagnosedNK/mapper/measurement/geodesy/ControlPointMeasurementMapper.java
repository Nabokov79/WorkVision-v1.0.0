package ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;

@Mapper(componentModel = "spring")
public interface ControlPointMeasurementMapper {

    @Mapping(source = "measurement.numberMeasurementLocation", target = "placeNumber")
    @Mapping(source = "measurement.controlPointValue", target = "calculatedHeight")
    @Mapping(source = "deviation", target = "deviation")
    @Mapping(source = "measurements", target = "geodesicMeasurements")
    @Mapping(target = "id", ignore = true)
    ControlPoint mapToControlPoint(GeodesicMeasurementsPoint measurement
                                , Integer deviation
                                , EquipmentGeodesicMeasurements measurements);

    @Mapping(source = "measurement.numberMeasurementLocation", target = "placeNumber")
    @Mapping(source = "measurement.controlPointValue", target = "calculatedHeight")
    @Mapping(source = "deviation", target = "deviation")
    @Mapping(target = "id", ignore = true)
    ControlPoint mapToUpdateControlPoint(@MappingTarget ControlPoint point
                                                      , GeodesicMeasurementsPoint measurement
                                                      , Integer deviation);
}