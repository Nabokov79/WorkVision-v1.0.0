package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableDeviationsGeodesy;

import java.util.List;
public interface PointDifferenceService {

    void save(AcceptableDeviationsGeodesy acceptableDeviationsGeodesy, List<GeodesicMeasurementsPoint> measurements);
}