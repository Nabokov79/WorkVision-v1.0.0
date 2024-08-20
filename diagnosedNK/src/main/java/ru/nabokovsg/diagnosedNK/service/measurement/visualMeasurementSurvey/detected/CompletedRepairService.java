package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.ResponseCompletedRepairDto;

import java.util.List;

public interface CompletedRepairService {

    ResponseCompletedRepairDto save(CompletedRepairDto repairDto);

    ResponseCompletedRepairDto update(CompletedRepairDto repairDto);

   List<ResponseCompletedRepairDto> getAll(Long equipmentId);

    void delete(Long id);
}