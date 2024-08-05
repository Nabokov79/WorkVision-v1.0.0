package ru.nabokovsg.laboratoryNK.service;

import ru.nabokovsg.laboratoryNK.dto.workJournal.WorkJournalDto;
import ru.nabokovsg.laboratoryNK.model.WorkJournal;

public interface JournalBuilderService {

    WorkJournal build(WorkJournalDto journalDto);
}