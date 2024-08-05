package ru.nabokovsg.documentNK.repository.template.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.common.DivisionType;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;

import java.util.Set;

public interface DocumentHeaderTemplateRepository extends JpaRepository<DocumentHeaderTemplate, Long> {

    Set<DocumentHeaderTemplate> findAllByDocumentTypeId(Long documentTypeId);

    DocumentHeaderTemplate findByDocumentTypeIdAndDivisionType(Long documentTypeId, DivisionType divisionType);
}