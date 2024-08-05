package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentInspection.EquipmentInspectionDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentInspection.ResponseEquipmentInspectionDto;

import java.util.List;

public interface EquipmentInspectionService {

    ResponseEquipmentInspectionDto save(EquipmentInspectionDto inspectionDto);

    ResponseEquipmentInspectionDto update(EquipmentInspectionDto inspectionDto);

    List<ResponseEquipmentInspectionDto> getAll(Long id);

    void delete(Long id);
}