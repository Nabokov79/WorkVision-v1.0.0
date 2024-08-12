package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseEquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.PointDifference;
import java.util.Set;

public interface EquipmentGeodesicMeasurementsService {

    ResponseEquipmentGeodesicMeasurementsDto get(Long equipmentId);

    EquipmentGeodesicMeasurements getByEquipmentId(Long equipmentId);

    void addControlPointAndPointDifference(Long equipmentId
                                         , Set<ControlPoint> controlPoints
                                         , Set<PointDifference> pointDifferences);
}