package ru.nabokovsg.documentNK.service.template.common;

import ru.nabokovsg.documentNK.dto.template.common.conclusion.ConclusionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.conclusion.ResponseConclusionTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.ConclusionTemplate;

public interface ConclusionTemplateService {

    ResponseConclusionTemplateDto save(ConclusionTemplateDto conclusionDto);

    ResponseConclusionTemplateDto update(ConclusionTemplateDto conclusionDto);

    ResponseConclusionTemplateDto get(Long id);

    ConclusionTemplate getByDocumentTypeId(Long documentTypeId);

    void delete(Long id);
}