package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderType;

import java.util.Map;

public interface CellFactoryService {

    CellTable createDateCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue);

    CellTable createSurveysDescriptionCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue);

    CellTable createDocumentNumberCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue);

    CellTable createOrganizationNameCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue);

    CellTable createRepairDescriptionCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue);

    CellTable create(ColumnHeaderType type, Integer mergeLines, Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue);
}