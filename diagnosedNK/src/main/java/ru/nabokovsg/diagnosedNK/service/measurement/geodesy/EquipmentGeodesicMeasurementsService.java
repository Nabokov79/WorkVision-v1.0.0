package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseEquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.PointDifference;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;

import java.util.List;
import java.util.Set;

public interface EquipmentGeodesicMeasurementsService {

    ResponseEquipmentGeodesicMeasurementsDto get(Long equipmentId);

    void addReferencePoint(Long equipmentId, List<ReferencePoint> referencePoints);

    void addControlPointAndPointDifference(Long equipmentId
                                         , Set<ControlPoint> controlPoints
                                         , Set<PointDifference> pointDifferences);
}