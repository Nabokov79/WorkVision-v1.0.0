package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.dto.workJournal.ResponseWorkJournalDto;
import ru.nabokovsg.laboratoryNK.dto.workJournal.WorkJournalDto;
import ru.nabokovsg.laboratoryNK.model.WorkJournal;

import java.time.LocalDate;
import java.util.List;

public interface WorkJournalService {

    ResponseWorkJournalDto save(WorkJournalDto journalDto);

    ResponseWorkJournalDto update(WorkJournalDto journalDto);

    WorkJournal getById(Long id);

    List<ResponseWorkJournalDto> getAll(LocalDate startPeriod, LocalDate endPeriod);

    void delete(Long id);
}