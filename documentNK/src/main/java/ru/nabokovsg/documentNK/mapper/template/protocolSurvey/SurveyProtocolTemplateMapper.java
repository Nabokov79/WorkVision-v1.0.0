package ru.nabokovsg.documentNK.mapper.template.protocolSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.client.templateCreate.DocumentTypeDto;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.ResponseSurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.ShortResponseSurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.SurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.ConclusionTemplate;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SurveyProtocolTemplateMapper {

    @Mapping(source = "protocolDto.documentTypeId", target = "documentTypeId")
    @Mapping(source = "protocolDto.equipmentTypeId", target = "equipmentTypeId")
    @Mapping(source = "documentHeaders", target = "leftHeaderTemplates")
    @Mapping(source = "documentType.title", target = "title")
    @Mapping(source = "documentType.subtitle", target = "subtitle")
    @Mapping(source = "conclusionTemplate", target = "conclusionTemplate")
    @Mapping(source = "protocolDto.id", target = "id")
    SurveyProtocolTemplate mapToProtocolTemplate(SurveyProtocolTemplateDto protocolDto
                                         , DocumentTypeDto documentType
                                         , Set<DocumentHeaderTemplate> documentHeaders
                                         , ConclusionTemplate conclusionTemplate);

    ShortResponseSurveyProtocolTemplateDto mapToShortResponseProtocolTemplateDto(SurveyProtocolTemplate protocolTemplate);

    ResponseSurveyProtocolTemplateDto mapToResponseProtocolTemplateDto(SurveyProtocolTemplate protocolTemplate);
}