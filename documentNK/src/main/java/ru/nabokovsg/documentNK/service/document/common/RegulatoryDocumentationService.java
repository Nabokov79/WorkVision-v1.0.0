package ru.nabokovsg.documentNK.service.document.common;

import ru.nabokovsg.documentNK.model.document.common.Subsection;
import ru.nabokovsg.documentNK.model.template.common.DocumentationTemplate;

import java.util.List;

public interface RegulatoryDocumentationService {

    void save(Subsection subsection, List<DocumentationTemplate> documentationTemplate);
}