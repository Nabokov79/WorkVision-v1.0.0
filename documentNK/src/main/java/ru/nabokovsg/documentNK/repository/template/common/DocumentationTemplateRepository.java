package ru.nabokovsg.documentNK.repository.template.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.common.DocumentationTemplate;

public interface DocumentationTemplateRepository extends JpaRepository<DocumentationTemplate, Long> {

    boolean existsByDocumentationId(Long documentationId);
}