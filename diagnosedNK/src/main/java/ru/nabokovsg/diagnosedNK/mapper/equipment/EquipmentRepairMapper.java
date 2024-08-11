package ru.nabokovsg.diagnosedNK.mapper.equipment;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentRepair.EquipmentRepairDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentRepair.ResponseEquipmentRepairDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentRepair;

@Mapper(componentModel = "spring")
public interface EquipmentRepairMapper {

    EquipmentRepair mapToEquipmentRepair(EquipmentRepairDto repairDto);

    ResponseEquipmentRepairDto mapToResponseEquipmentRepairDto(EquipmentRepair equipmentRepair);
}