package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.inspection.ResponseInspectionDto;

import java.util.List;

public interface InspectionService {

    ResponseInspectionDto save(InspectionDto inspectionDto);

    List<ResponseInspectionDto> getAll(Long equipmentId);
}