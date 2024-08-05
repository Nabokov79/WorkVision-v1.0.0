package ru.nabokovsg.laboratoryNK.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.laboratoryNK.dto.workJournal.ResponseWorkJournalDto;
import ru.nabokovsg.laboratoryNK.model.WorkJournal;

@Mapper(componentModel = "spring")
public interface WorkJournalMapper {

    WorkJournal mapToUpdateWorkJournal(@MappingTarget WorkJournal journal, WorkJournal journalDto);

    ResponseWorkJournalDto mapToResponseWorkJournalDto(WorkJournal journal);
}