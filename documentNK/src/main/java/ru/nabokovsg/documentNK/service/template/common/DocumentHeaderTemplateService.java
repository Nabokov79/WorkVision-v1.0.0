package ru.nabokovsg.documentNK.service.template.common;

import ru.nabokovsg.documentNK.dto.template.common.documentHeader.DocumentHeaderTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.documentHeader.ResponseDocumentHeaderTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;

import java.util.List;
import java.util.Set;

public interface DocumentHeaderTemplateService {

    ResponseDocumentHeaderTemplateDto save(DocumentHeaderTemplateDto headerDto);

    ResponseDocumentHeaderTemplateDto update(DocumentHeaderTemplateDto headerDto);

    List<ResponseDocumentHeaderTemplateDto> getAll(Long id);

    Set<DocumentHeaderTemplate> getAllByDocumentTypeId(Long documentTypeId);

    void delete(Long id);
}