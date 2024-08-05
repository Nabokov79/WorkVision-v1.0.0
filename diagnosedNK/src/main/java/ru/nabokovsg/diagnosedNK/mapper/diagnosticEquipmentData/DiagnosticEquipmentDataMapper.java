package ru.nabokovsg.diagnosedNK.mapper.diagnosticEquipmentData;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData.EquipmentDto;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;

@Mapper(componentModel = "spring")
public interface DiagnosticEquipmentDataMapper {

    @Mapping(source = "equipmentDto.id", target = "equipmentId")
    @Mapping(source = "equipmentDto.volume", target = "volume")
    @Mapping(source = "equipmentDto.old", target = "old")
    @Mapping(source = "equipmentDto.geodesyLocations", target = "geodesyLocations")
    @Mapping(target = "id", ignore = true)
    DiagnosticEquipmentData mapToDiagnosticObjectData(EquipmentDto equipmentDto);


    @Mapping(source = "equipmentDto.id", target = "equipmentId")
    @Mapping(source = "equipmentDto.volume", target = "volume")
    @Mapping(source = "equipmentDto.old", target = "old")
    @Mapping(source = "equipmentDto.geodesyLocations", target = "geodesyLocations")
    @Mapping(source = "object.id", target = "id")
    DiagnosticEquipmentData mapToUpdateDiagnosticObjectData(DiagnosticEquipmentData object, EquipmentDto equipmentDto);
}