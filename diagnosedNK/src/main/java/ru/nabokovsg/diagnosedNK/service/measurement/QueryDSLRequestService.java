package ru.nabokovsg.diagnosedNK.service.measurement;

import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.PointDifference;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.IdentifiedDefect;

import java.util.Set;

public interface QueryDSLRequestService {

    IdentifiedDefect getIdentifiedDefect(IdentifiedDefectDto defectDto);

    CompletedRepair getCompletedRepair(CompletedRepairDto repairDto);

    DiagnosticEquipmentData getDiagnosticEquipmentData(Long elementId, Long partElementId);

    Set<ReferencePoint> getAllReferencePoint(Long equipmentId);

    Set<ControlPoint> getAllControlPoint(Long equipmentId);

    Set<PointDifference> getAllPointDifference(Long equipmentId);
}