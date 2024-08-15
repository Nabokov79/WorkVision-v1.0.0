package ru.nabokovsg.diagnosedNK.service.measurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.PointDifference;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UTPredicateData;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.IdentifiedDefect;

import java.util.Set;

public interface QueryDSLRequestService {

    IdentifiedDefect getIdentifiedDefect(IdentifiedDefectDto defectDto);

    CompletedRepair getCompletedRepair(CompletedRepairDto repairDto);

    Long getEquipmentTypeId(Long elementId);

    Set<ReferencePoint> getAllReferencePoint(Long equipmentId);

    Set<ControlPoint> getAllControlPoint(Long equipmentId);

    Set<PointDifference> getAllPointDifference(Long equipmentId);

    UltrasonicThicknessMeasurement getUltrasonicThicknessMeasurement(UTPredicateData predicateData);
}