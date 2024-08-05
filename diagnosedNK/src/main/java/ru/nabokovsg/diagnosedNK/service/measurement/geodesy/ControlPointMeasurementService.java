package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;

import java.util.List;
import java.util.Set;

public interface ControlPointMeasurementService {

    Set<ControlPoint> save(List<GeodesicMeasurementsPoint> measurements);
}