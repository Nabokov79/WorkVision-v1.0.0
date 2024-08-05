package ru.nabokovsg.documentNK.repository.template.reportSurvey;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.reportSurvey.PageTitleTemplate;

import java.util.Optional;

public interface PageTitleTemplateRepository extends JpaRepository<PageTitleTemplate, Long> {

    Optional<PageTitleTemplate> findByDocumentTypeIdAndEquipmentTypeId(Long documentTypeId, Long equipmentTypeId);

    boolean existsByDocumentTypeIdAndEquipmentTypeId(Long documentTypeId, Long equipmentTypeId);
}