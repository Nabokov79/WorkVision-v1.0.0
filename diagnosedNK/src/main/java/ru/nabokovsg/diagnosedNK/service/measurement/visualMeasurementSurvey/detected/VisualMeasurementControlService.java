package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.visualMeasuringSurvey.ResponseCalculatedVMSurveyDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.VisualMeasurementControl;

import java.util.List;
import java.util.Set;

public interface VisualMeasurementControlService {

    List<ResponseCalculatedVMSurveyDto> getAll(Long equipmentId);

    VisualMeasurementControl get(Long equipmentId, Long elementId);

    Set<VisualMeasurementControl> getByEquipmentId(Long equipmentId);

    VisualMeasurementControl addInspection(InspectionDto inspectionDto);
}