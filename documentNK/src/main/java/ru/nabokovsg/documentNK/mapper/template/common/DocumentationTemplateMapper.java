package ru.nabokovsg.documentNK.mapper.template.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.template.common.documentationTemplate.DocumentationTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.documentationTemplate.ResponseDocumentationTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.DocumentationTemplate;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;

@Mapper(componentModel = "spring")
public interface DocumentationTemplateMapper {

    @Mapping(source = "documentationDto.documentationId", target = "documentationId")
    @Mapping(source = "documentationDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "documentName", target = "documentName")
    @Mapping(source = "template", target = "subsectionTemplate")
    @Mapping(source = "documentationDto.id", target = "id")
    DocumentationTemplate mapToDocumentationTemplate(DocumentationTemplateDto documentationDto
                                                   , String documentName
                                                   , SubsectionTemplate template);

    ResponseDocumentationTemplateDto mapToResponseDocumentationTemplateDto(DocumentationTemplate template);
}