package ru.nabokovsg.documentNK.mapper.template.protocolControl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.client.templateCreate.DocumentTypeDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ResponseProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ShortResponseProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProtocolControlTemplateMapper {

    @Mapping(source = "protocolDto.documentTypeId", target = "documentTypeId")
    @Mapping(source = "documentHeaders", target = "leftHeaderTemplates")
    @Mapping(source = "documentType.title", target = "title")
    @Mapping(source = "documentType.subtitle", target = "subtitle")
    @Mapping(source = "tableTemplate", target = "tableTemplate")
    @Mapping(source = "protocolDto.id", target = "id")
    ProtocolControlTemplate mapToProtocolTemplate(ProtocolControlTemplateDto protocolDto
                                                , DocumentTypeDto documentType
                                                , Set<DocumentHeaderTemplate> documentHeaders
                                                , TableTemplate tableTemplate);

    ShortResponseProtocolControlTemplateDto mapToShortResponseProtocolTemplateDto(ProtocolControlTemplate protocolTemplate);

    ResponseProtocolControlTemplateDto mapToResponseProtocolTemplateDto(ProtocolControlTemplate protocolTemplate);
}