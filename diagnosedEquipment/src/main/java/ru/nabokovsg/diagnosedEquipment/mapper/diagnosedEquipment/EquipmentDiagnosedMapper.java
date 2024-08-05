package ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed.EquipmentDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed.ResponseEquipmentDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed.ResponseShortEquipmentDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentDiagnosed;
import ru.nabokovsg.diagnosedEquipment.model.equipmentType.EquipmentType;

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
    EquipmentDiagnosed mapToEquipment(EquipmentDto equipmentDto, EquipmentType equipmentType);

    ResponseShortEquipmentDto mapToResponseShortEquipmentDto(EquipmentDiagnosed equipment);

    ResponseEquipmentDto mapToResponseEquipmentDto(EquipmentDiagnosed equipment);
}