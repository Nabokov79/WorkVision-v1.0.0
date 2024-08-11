package ru.nabokovsg.diagnosedNK.mapper.equipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentDiagnosed.EquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentDiagnosed.ResponseEquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentDiagnosed.ResponseShortEquipmentDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentDiagnosed;
import ru.nabokovsg.diagnosedNK.model.equipmentType.EquipmentType;

@Mapper(componentModel = "spring")
public interface EquipmentDiagnosedMapper {

    @Mapping(source = "equipmentType.id", target = "equipmentTypeId")
    @Mapping(source = "equipmentDto.buildingId", target = "buildingId")
    @Mapping(source = "equipmentType.equipmentName", target = "equipmentName")
    @Mapping(source = "equipmentDto.stationaryNumber", target = "stationaryNumber")
    @Mapping(source = "equipmentType.volume", target = "volume")
    @Mapping(source = "equipmentType.model", target = "model")
    @Mapping(source = "equipmentDto.geodesyLocations", target = "geodesyLocations")
    @Mapping(source = "equipmentDto.id", target = "id")
    @Mapping(target = "elements", ignore = true)
    EquipmentDiagnosed mapToEquipment(EquipmentDto equipmentDto, EquipmentType equipmentType);

    ResponseShortEquipmentDto mapToResponseShortEquipmentDto(EquipmentDiagnosed equipment);

    ResponseEquipmentDto mapToResponseEquipmentDto(EquipmentDiagnosed equipment);
}