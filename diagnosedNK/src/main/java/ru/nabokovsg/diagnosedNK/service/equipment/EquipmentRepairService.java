package ru.nabokovsg.diagnosedNK.service.equipment;

import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentRepair.EquipmentRepairDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentRepair.ResponseEquipmentRepairDto;

import java.util.List;

public interface EquipmentRepairService {

    ResponseEquipmentRepairDto save(EquipmentRepairDto repairDto);

    ResponseEquipmentRepairDto update(EquipmentRepairDto repairDto);

    List<ResponseEquipmentRepairDto> getAll(Long id);

    void delete(Long id);
}