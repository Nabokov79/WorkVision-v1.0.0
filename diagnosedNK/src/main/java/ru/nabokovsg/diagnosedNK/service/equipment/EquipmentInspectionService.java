package ru.nabokovsg.diagnosedNK.service.equipment;

import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentInspection.EquipmentInspectionDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentInspection.ResponseEquipmentInspectionDto;

import java.util.List;

public interface EquipmentInspectionService {

    ResponseEquipmentInspectionDto save(EquipmentInspectionDto inspectionDto);

    ResponseEquipmentInspectionDto update(EquipmentInspectionDto inspectionDto);

    List<ResponseEquipmentInspectionDto> getAll(Long id);

    void delete(Long id);
}