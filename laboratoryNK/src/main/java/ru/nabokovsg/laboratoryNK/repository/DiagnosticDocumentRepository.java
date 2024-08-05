package ru.nabokovsg.laboratoryNK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocument;

public interface DiagnosticDocumentRepository extends JpaRepository<DiagnosticDocument, Long> {

    DiagnosticDocument findByJournalId(Long workJournalId);
}