package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.common;

import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.PointDifference;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UTPredicateData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;

import java.util.Set;

public interface QueryDSLRequestService {

    Long getEquipmentTypeId(Long elementId);

    Set<ReferencePoint> getAllReferencePoint(Long equipmentId);

    Set<ControlPoint> getAllControlPoint(Long equipmentId);

    Set<PointDifference> getAllPointDifference(Long equipmentId);

    UltrasonicThicknessMeasurement getUltrasonicThicknessMeasurement(UTPredicateData predicateData);

    Double getMaxCorrosionValueByPredicate(UltrasonicThicknessMeasurementDto measurementDto, Long equipmentId);
}