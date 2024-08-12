package ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;

@Mapper(componentModel = "spring")
public interface ReferencePointMapper {

    @Mapping(source = "deviation", target = "deviation")
    @Mapping(source = "precipitation", target = "precipitation")
    @Mapping(source = "acceptablePrecipitation", target = "acceptablePrecipitation")
    ReferencePoint mapWithReferencePointData(@MappingTarget ReferencePoint point
                                           , Integer deviation
                                           , Integer precipitation
                                           , Boolean acceptablePrecipitation);

    @Mapping(source = "measurement.numberMeasurementLocation", target = "placeNumber")
    @Mapping(source = "measurement.referencePointValue", target = "calculatedHeight")
    @Mapping(source = "measurements", target = "geodesicMeasurements")
    @Mapping(target = "id", ignore = true)
    ReferencePoint mapToReferencePoint(GeodesicMeasurementsPoint measurement
                                   , EquipmentGeodesicMeasurements measurements);

    @Mapping(source = "measurement.numberMeasurementLocation", target = "placeNumber")
    @Mapping(source = "measurement.referencePointValue", target = "calculatedHeight")
    @Mapping(target = "id", ignore = true)
    ReferencePoint mapToUpdateReferencePoint(@MappingTarget ReferencePoint point
                                                          , GeodesicMeasurementsPoint measurement);
}