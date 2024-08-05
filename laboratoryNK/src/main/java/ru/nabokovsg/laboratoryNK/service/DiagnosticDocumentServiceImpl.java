package ru.nabokovsg.laboratoryNK.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.laboratoryNK.client.LaboratoryClient;
import ru.nabokovsg.laboratoryNK.dto.diagnosticDocument.DiagnosticDocumentDto;
import ru.nabokovsg.laboratoryNK.dto.workJournal.WorkJournalDto;
import ru.nabokovsg.laboratoryNK.exceptions.NotFoundException;
import ru.nabokovsg.laboratoryNK.mapper.DiagnosticDocumentMapper;
import ru.nabokovsg.laboratoryNK.model.*;
import ru.nabokovsg.laboratoryNK.repository.DiagnosticDocumentRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
@RequiredArgsConstructor
public class DiagnosticDocumentServiceImpl extends DiagnosticDocumentStatusFactory implements DiagnosticDocumentService {

    private final DiagnosticDocumentRepository repository;
    private final DiagnosticDocumentMapper mapper;
    private final EntityManager em;
    private final DiagnosticDocumentTypeService documentTypeService;
    private final MeasuringToolService toolService;
    private final StringBuilderService builderService;
    private final LaboratoryClient client;

    @Override
    public void save(WorkJournalDto journalDto, WorkJournal journal) {
        if (journal.getDate() != null) {
            DiagnosticDocumentType diagnosticDocumentType = documentTypeService.getById(
                                                                              journalDto.getDiagnosticDocumentTypeId());
            repository.save(mapper.mapToDiagnosticDocument(journal
                                                    , journalDto.getEquipmentId()
                                                    , diagnosticDocumentType
                                                    , builderService.buildDiagnosticDocumentType(diagnosticDocumentType)
                                                    , getDiagnosticDocumentStatus(DocumentStatus.NEW_DOCUMENT)
                                            , getDocumentNumber()));
        }
    }

    @Override
    public void update(WorkJournalDto journalDto, WorkJournal journal) {
        DiagnosticDocument document = repository.findByJournalId(journal.getId());
        if (document != null) {
            if (document.getStatus().equals(getDiagnosticDocumentStatus(DocumentStatus.NEW_DOCUMENT))) {
                DiagnosticDocumentType diagnosticDocumentType = documentTypeService.getById(
                                                                              journalDto.getDiagnosticDocumentTypeId());
                repository.save(
                        mapper.mapToUpdateDiagnosticDocument(document
                                                , journal
                                                , journalDto.getEquipmentId()
                                                , getDiagnosticDocumentStatus(DocumentStatus.VERIFICATION)
                                                , diagnosticDocumentType
                                                , builderService.buildDiagnosticDocumentType(diagnosticDocumentType))
                );
            }
        } else {
            save(journalDto, journal);
        }
    }

    @Override
    public List<DiagnosticDocumentDto> getAll(LocalDate startPeriod, LocalDate endPeriod, boolean week, boolean month) {
        QDiagnosticDocument document = QDiagnosticDocument.diagnosticDocument;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (startPeriod == null || endPeriod == null) {
            LocalDate now = LocalDate.now();
            if (week) {
                startPeriod = now.with(now.with(DayOfWeek.MONDAY).minusDays(1));
                endPeriod = now.with(now.with(DayOfWeek.SUNDAY).plusDays(1));
            }
            if (month) {
                startPeriod = now.with(ChronoField.DAY_OF_MONTH, 1).minusDays(1);
                endPeriod = now.with(ChronoField.DAY_OF_MONTH, now.lengthOfMonth()).plusDays(1);
            }
            if (!month && !week) {
                startPeriod = now.with(ChronoField.DAY_OF_MONTH, 1).minusDays(1);
                endPeriod = now.with(ChronoField.DAY_OF_MONTH, now.lengthOfMonth()).plusDays(1);
            }
        }
        booleanBuilder.and(document.date.after(startPeriod));
        booleanBuilder.and(document.date.before(endPeriod));
        return new JPAQueryFactory(em).from(document)
                .select(document)
                .where(booleanBuilder)
                .fetch()
                .stream()
                .map(mapper::mapToDiagnosticDocumentDto)
                .toList();
    }

    @Override
    public String create(Long workJournalId) {
        DiagnosticDocument document = repository.findByJournalId(workJournalId);
        if (document == null) {
            throw new NotFoundException(
                    String.format("Diagnostic document by workJournalId=%s not found", workJournalId));
        }
        return client.createDocument(document.getDiagnosticDocumentType().getTypeDocument()
                                   , mapper.mapToDocumentCreationDataDto(document
                                   , toolService.getAllLaboratoryEmployeeId(document.getJournal()
                                                                                    .getEmployees()
                                                                                    .stream()
                                                                                    .map(LaboratoryEmployee::getId)
                                                                                    .toList())));
    }

    @Override
    public DiagnosticDocument getById(Long id) {
        return repository.findById(id)
               .orElseThrow(() -> new NotFoundException(String.format("Diagnostic document with id=%s not found", id)));
    }

    @Override
    public void updateStatus(DiagnosticDocument document, DocumentStatus status) {
        document.setStatus(getDiagnosticDocumentStatus(status));
        repository.save(document);
    }

    private Integer getDocumentNumber() {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.with(firstDayOfYear());
        LocalDate lastDay = now.with(lastDayOfYear());
        QDiagnosticDocument document = QDiagnosticDocument.diagnosticDocument;
        Integer number = new JPAQueryFactory(em).from(document)
                .select(document.documentNumber.max())
                .where(document.date.after(firstDay).and(document.date.before(lastDay)))
                .fetchOne();
        if (number == null) {
            return 1;
        }
        return (++number);
    }
}