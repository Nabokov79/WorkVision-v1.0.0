package ru.nabokovsg.documentNK.mapper.template.reportSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.client.templateCreate.DocumentTypeDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle.PageTitleTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle.ResponsePageTitleTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.PageTitleTemplate;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PageTitleTemplateMapper {

    @Mapping(source = "documentHeaders", target = "documentHeaders")
    @Mapping(source = "documentType.title", target = "title")
    @Mapping(source = "documentType.subtitle", target = "subtitle")
    @Mapping(source = "pageTitleDto.equipmentText", target = "equipmentText")
    @Mapping(source = "pageTitleDto.city", target = "city")
    @Mapping(source = "pageTitleDto.id", target = "id")
    PageTitleTemplate mapToPageTitleTemplate(PageTitleTemplateDto pageTitleDto
                                           , DocumentTypeDto documentType
                                           , Set<DocumentHeaderTemplate> documentHeaders);

    ResponsePageTitleTemplateDto mapToResponsePageTitleTemplateDto(PageTitleTemplate pageTitle);
}