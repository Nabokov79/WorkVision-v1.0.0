package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.remarkByEquipment.RemarkByEquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.remarkByEquipment.ResponseRemarkByEquipmentDto;

import java.util.List;

public interface RemarkByEquipmentService {

    ResponseRemarkByEquipmentDto save(RemarkByEquipmentDto inspectionDto);

    ResponseRemarkByEquipmentDto update(RemarkByEquipmentDto remarkDto);

    List<ResponseRemarkByEquipmentDto> getAll(Long equipmentId);
}