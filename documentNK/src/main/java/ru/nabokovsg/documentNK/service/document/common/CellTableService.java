package ru.nabokovsg.documentNK.service.document.common;

import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;

import java.util.List;

public interface CellTableService {

    void save(DocumentTable table, List<CellTable> cells);
}