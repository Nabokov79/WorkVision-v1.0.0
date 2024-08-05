package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.dto.diagnosticDocument.DiagnosticDocumentDto;
import ru.nabokovsg.laboratoryNK.dto.workJournal.WorkJournalDto;
import ru.nabokovsg.laboratoryNK.model.DiagnosticDocument;
import ru.nabokovsg.laboratoryNK.model.DocumentStatus;
import ru.nabokovsg.laboratoryNK.model.WorkJournal;

import java.time.LocalDate;
import java.util.List;

public interface DiagnosticDocumentService {

    void save(WorkJournalDto journalDto, WorkJournal journal);

    void update(WorkJournalDto journalDto, WorkJournal journal);

    List<DiagnosticDocumentDto> getAll(LocalDate startPeriod, LocalDate endPeriod, boolean week, boolean month);

    String create(Long workJournalId);

    DiagnosticDocument getById(Long id);

    void updateStatus(DiagnosticDocument document, DocumentStatus status);
}