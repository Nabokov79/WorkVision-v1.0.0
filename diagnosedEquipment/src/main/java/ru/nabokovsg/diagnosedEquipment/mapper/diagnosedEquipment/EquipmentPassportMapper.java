package ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentPassport.EquipmentPassportDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentPassport.ResponseEquipmentPassportDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentDiagnosed;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentPassport;

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