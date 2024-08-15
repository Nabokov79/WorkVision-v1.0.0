package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.visualMeasuringSurvey.ResponseCalculatedVMSurveyDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CalculatedVMSurvey;

import java.util.List;
import java.util.Set;

public interface CalculatedVMSurveyService {

    List<ResponseCalculatedVMSurveyDto> getAll(Long equipmentId);

    CalculatedVMSurvey get(Long equipmentId, Long elementId);

    Set<CalculatedVMSurvey> getByEquipmentId(Long workJournalId);

    CalculatedVMSurvey addInspection(InspectionDto inspectionDto);
}