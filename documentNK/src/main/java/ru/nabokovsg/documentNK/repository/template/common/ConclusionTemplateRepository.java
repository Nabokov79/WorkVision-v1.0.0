package ru.nabokovsg.documentNK.repository.template.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.common.ConclusionTemplate;

import java.util.Optional;

public interface ConclusionTemplateRepository extends JpaRepository<ConclusionTemplate, Long> {

    boolean existsByDocumentTypeId(Long documentTypeId);

    Optional<ConclusionTemplate> findByDocumentTypeId(Long documentTypeId);
}