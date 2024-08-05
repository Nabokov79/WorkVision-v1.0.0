package ru.nabokovsg.documentNK.mapper.document.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentPassportDto;
import ru.nabokovsg.documentNK.model.document.common.DocumentEquipmentPassport;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.Section;

@Mapper(componentModel = "spring")
public interface DocumentEquipmentPassportMapper {

    @Mapping(source = "passportDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "passportDto.header", target = "header")
    @Mapping(source = "passportDto.meaning", target = "meaning")
    @Mapping(source = "section", target = "section")
    @Mapping(target = "surveyProtocol", ignore = true)
    @Mapping(target = "id", ignore = true)
    DocumentEquipmentPassport mapForSection(Section section, EquipmentPassportDto passportDto);

    @Mapping(source = "passportDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "passportDto.header", target = "header")
    @Mapping(source = "passportDto.meaning", target = "meaning")
    @Mapping(source = "protocol", target = "surveyProtocol")
    @Mapping( target = "section", ignore = true)
    @Mapping(target = "id", ignore = true)
    DocumentEquipmentPassport mapForSurveyProtocol(SurveyProtocol protocol, EquipmentPassportDto passportDto);
}