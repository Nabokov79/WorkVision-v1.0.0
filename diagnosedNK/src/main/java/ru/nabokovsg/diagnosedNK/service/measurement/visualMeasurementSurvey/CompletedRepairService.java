package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair.ResponseCompletedRepairDto;

import java.util.List;

public interface CompletedRepairService {

    ResponseCompletedRepairDto save(CompletedRepairDto repairDto);

   List<ResponseCompletedRepairDto> getAll(Long equipmentId);

    void delete(Long id);
}