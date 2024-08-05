package ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicPointType;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.PointDifference;

@Mapper(componentModel = "spring")
public interface PointDifferenceMapper {

    @Mapping(source = "geodesicPointType", target = "geodesicPointType")
    @Mapping(source = "firstPlaceNumber", target = "firstPlaceNumber")
    @Mapping(source = "secondPlaceNumber", target = "secondPlaceNumber")
    @Mapping(source = "difference", target = "difference")
    @Mapping(target = "id", ignore = true)
    PointDifference mapToPointDifference(GeodesicPointType geodesicPointType
                                       , ControlPoint controlPoint
                                       , Integer firstPlaceNumber
                                       , Integer secondPlaceNumber
                                       , Integer difference);

    @Mapping(source = "acceptableDifference", target = "acceptableDifference")
    @Mapping(target = "id", ignore = true)
    PointDifference mapPointDifferenceWithControlPointMeasurement(@MappingTarget PointDifference pointDifference
                                                                               , Boolean acceptableDifference);

    PointDifference mapToUpdatePointDifference(@MappingTarget PointDifference pointDifferenceDb
                                                            , PointDifference pointDifference);
}