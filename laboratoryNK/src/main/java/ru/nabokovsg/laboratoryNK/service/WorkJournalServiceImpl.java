package ru.nabokovsg.laboratoryNK.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.laboratoryNK.dto.workJournal.ResponseWorkJournalDto;
import ru.nabokovsg.laboratoryNK.dto.workJournal.WorkJournalDto;
import ru.nabokovsg.laboratoryNK.exceptions.BadRequestException;
import ru.nabokovsg.laboratoryNK.exceptions.NotFoundException;
import ru.nabokovsg.laboratoryNK.mapper.WorkJournalMapper;
import ru.nabokovsg.laboratoryNK.model.QWorkJournal;
import ru.nabokovsg.laboratoryNK.model.WorkJournal;
import ru.nabokovsg.laboratoryNK.repository.WorkJournalRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkJournalServiceImpl implements WorkJournalService {

    private final WorkJournalRepository repository;
    private final WorkJournalMapper mapper;
    private final EntityManager em;
    private final DiagnosticDocumentService documentService;
    private final JournalBuilderService journalBuilderService;

    @Override
    public ResponseWorkJournalDto save(WorkJournalDto journalDto) {
        if (repository.existsByDateAndEquipmentId(journalDto.getDate(), journalDto.getEquipmentId())) {
            throw new BadRequestException(
                    String.format("A log entry was found by date=%s and equipmentId=%s", journalDto.getDate()
                                                                                       , journalDto.getEquipmentId())
            );
        }
        WorkJournal journal = repository.save(journalBuilderService.build(journalDto));
        documentService.save(journalDto, journal);
        return mapper.mapToResponseWorkJournalDto(journal);
    }

    @Override
    public ResponseWorkJournalDto update(WorkJournalDto journalDto) {
        WorkJournal journal = getById(journalDto.getId());
        if (journal != null) {
            journal = repository.save(mapper.mapToUpdateWorkJournal(journal
                                                                  , journalBuilderService.build(journalDto)));
            documentService.save(journalDto, journal);
            return  mapper.mapToResponseWorkJournalDto(journal);
        }
        throw new NotFoundException(
                String.format("WorkJournal with id=%s not found for update", journalDto.getId())
        );
    }

    @Override
    public WorkJournal getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->  new NotFoundException(
                        String.format("WorkJournal with id=%s not found for delete", id)));
    }

    @Override
    public List<ResponseWorkJournalDto> getAll(LocalDate startPeriod, LocalDate endPeriod) {
        QWorkJournal journal = QWorkJournal.workJournal;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (startPeriod != null && endPeriod != null) {
            booleanBuilder.and(journal.date.after(startPeriod));
            booleanBuilder.and(journal.date.before(endPeriod));
        } else {
            booleanBuilder.and(journal.date.eq(LocalDate.now()));
        }
        return new JPAQueryFactory(em).from(journal)
                .select(journal)
                .where(booleanBuilder)
                .fetch()
                .stream()
                .map(mapper::mapToResponseWorkJournalDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("WorkJournal with id=%s not found for delete", id));
    }
}