package ru.nabokovsg.diagnosedNK.mapper.equipment;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentInspection.EquipmentInspectionDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentInspection.ResponseEquipmentInspectionDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentInspection;

@Mapper(componentModel = "spring")
public interface EquipmentInspectionMapper {

    EquipmentInspection mapToEquipmentInspection(EquipmentInspectionDto inspectionDto);

    ResponseEquipmentInspectionDto mapToResponseEquipmentInspectionDto(EquipmentInspection inspection);
}