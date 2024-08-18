package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated;

import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.visualMeasuringSurvey.ResponseCalculatedVMSurveyDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedVMSurvey;

import java.util.List;
import java.util.Set;

public interface CalculatedVMSurveyService {

    List<ResponseCalculatedVMSurveyDto> getAll(Long equipmentId);

    CalculatedVMSurvey get(Long equipmentId, Long elementId);

    Set<CalculatedVMSurvey> getByEquipmentId(Long equipmentId);

    CalculatedVMSurvey addInspection(InspectionDto inspectionDto);
}