package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.List;

public interface TableUCCellCreateService {

    List<CellTable> create(TableTemplate template, WorkJournalDto journal);
}