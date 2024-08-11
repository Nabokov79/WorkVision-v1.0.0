package ru.nabokovsg.diagnosedNK.mapper.equipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPassport.EquipmentPassportDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPassport.ResponseEquipmentPassportDto;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentDiagnosed;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPassport;

@Mapper(componentModel = "spring")
public interface EquipmentPassportMapper {

    @Mapping(source = "passportDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "passportDto.header", target = "header")
    @Mapping(source = "passportDto.meaning", target = "meaning")
    @Mapping(source = "passportDto.useToProtocol", target = "useToProtocol")
    @Mapping(source = "equipmentDiagnosed.id", target = "equipmentDiagnosedId")
    @Mapping(source = "passportDto.id", target = "id")
    EquipmentPassport mapToEquipmentPassport(EquipmentPassportDto passportDto, EquipmentDiagnosed equipmentDiagnosed);

    ResponseEquipmentPassportDto mapToResponseEquipmentPassportDto(EquipmentPassport passport);
}