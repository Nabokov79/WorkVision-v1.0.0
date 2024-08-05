package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ExaminedPartElement;

public interface ExaminedPartElementService {

    ExaminedPartElement get(Long equipmentId, Long partElementId);

    ExaminedPartElement addInspection(InspectionDto inspectionDto);
}