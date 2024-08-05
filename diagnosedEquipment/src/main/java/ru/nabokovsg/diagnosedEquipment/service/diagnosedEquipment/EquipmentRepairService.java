package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentRepair.EquipmentRepairDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentRepair.ResponseEquipmentRepairDto;

import java.util.List;

public interface EquipmentRepairService {

    ResponseEquipmentRepairDto save(EquipmentRepairDto repairDto);

    ResponseEquipmentRepairDto update(EquipmentRepairDto repairDto);

    List<ResponseEquipmentRepairDto> getAll(Long id);

    void delete(Long id);
}