package ru.nabokovsg.documentNK.repository.template.protocolSurvey;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;

import java.util.Optional;

public interface SurveyProtocolTemplateRepository extends JpaRepository<SurveyProtocolTemplate, Long> {

    boolean existsByDocumentTypeIdAndEquipmentTypeId(Long documentTypeId, Long equipmentTypeId);

    Optional<SurveyProtocolTemplate> findByDocumentTypeIdAndEquipmentTypeId(Long documentTypeId, Long equipmentTypeId);
}