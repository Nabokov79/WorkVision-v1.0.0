package ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentRepair.EquipmentRepairDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentRepair.ResponseEquipmentRepairDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentRepair;

@Mapper(componentModel = "spring")
public interface EquipmentRepairMapper {

    EquipmentRepair mapToEquipmentRepair(EquipmentRepairDto repairDto);

    ResponseEquipmentRepairDto mapToResponseEquipmentRepairDto(EquipmentRepair equipmentRepair);
}