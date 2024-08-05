package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.visualMeasuringSurvey.ResponseVisualMeasuringSurveyDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.VisualMeasuringSurvey;

import java.util.List;
import java.util.Set;

public interface VisualMeasuringSurveyService {

    List<ResponseVisualMeasuringSurveyDto> getAll(Long equipmentId);

    VisualMeasuringSurvey get(Long equipmentId, Long elementId);

    Set<VisualMeasuringSurvey> getByEquipmentId(Long workJournalId);

    VisualMeasuringSurvey addInspection(InspectionDto inspectionDto);
}