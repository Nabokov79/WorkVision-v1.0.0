package ru.nabokovsg.diagnosedNK.service.measurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.PointDifference;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UTPredicateData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.IdentifiedDefect;

import java.util.Set;

public interface QueryDSLRequestService {

    IdentifiedDefect getIdentifiedDefect(IdentifiedDefectDto defectDto);

    CompletedRepair getCompletedRepair(CompletedRepairDto repairDto);

    Set<IdentifiedDefect> getAllIdentifiedDefect(IdentifiedDefectDto defectDto);

    Set<CompletedRepair> getAllCompletedRepair(CompletedRepairDto repairDto);

    Long getEquipmentTypeId(Long elementId);

    Set<ReferencePoint> getAllReferencePoint(Long equipmentId);

    Set<ControlPoint> getAllControlPoint(Long equipmentId);

    Set<PointDifference> getAllPointDifference(Long equipmentId);

    UltrasonicThicknessMeasurement getUltrasonicThicknessMeasurement(UTPredicateData predicateData);

    Double getMaxCorrosionValueByPredicate(UltrasonicThicknessMeasurementDto measurementDto, Long equipmentId);
}