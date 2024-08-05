package ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment;

import org.mapstruct.Mapper;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentInspection.EquipmentInspectionDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentInspection.ResponseEquipmentInspectionDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentInspection;

@Mapper(componentModel = "spring")
public interface EquipmentInspectionMapper {

    EquipmentInspection mapToEquipmentInspection(EquipmentInspectionDto inspectionDto);

    ResponseEquipmentInspectionDto mapToResponseEquipmentInspectionDto(EquipmentInspection inspection);
}