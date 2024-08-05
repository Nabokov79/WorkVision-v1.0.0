package ru.nabokovsg.documentNK.service.template.common;

import ru.nabokovsg.documentNK.dto.template.common.documentationTemplate.DocumentationTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.documentationTemplate.ResponseDocumentationTemplateDto;

import java.util.List;

public interface DocumentationTemplateService {

    ResponseDocumentationTemplateDto save(DocumentationTemplateDto documentationDto);

    ResponseDocumentationTemplateDto update(DocumentationTemplateDto documentationDto);

    List<ResponseDocumentationTemplateDto> getAll();

   void delete(Long id);
}