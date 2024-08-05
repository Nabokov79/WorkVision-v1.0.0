package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.mapper.document.common.CellTableMapper;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderType;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CellFactoryServiceImpl implements CellFactoryService {

    private final CellTableMapper mapper;
    private final static String NO_DEFECT = "Дефекты не обнаружены";
    private final static String MISSING = "отсутствуют";
    private final static String DASH = "-";

    @Override
    public CellTable createDateCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue) {
        return mapper.mapToCell(columnHeaders.get(String.valueOf(ColumnHeaderType.DATE)), stringSequentialNumber, valid(cellValue));
    }

    @Override
    public CellTable createSurveysDescriptionCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue) {
        return mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.SURVEYS_DESCRIPTION)), stringSequentialNumber, valid(cellValue));
    }

    @Override
    public CellTable createDocumentNumberCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue) {
        return mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.DOCUMENT_NUMBER)), stringSequentialNumber, valid(cellValue));
    }

    @Override
    public CellTable createOrganizationNameCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue) {
        return mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.ORGANIZATION_NAME)), stringSequentialNumber, valid(cellValue));
    }

    @Override
    public CellTable createRepairDescriptionCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue) {
        return mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.REPAIR_DESCRIPTION)), stringSequentialNumber, valid(cellValue));
    }

    @Override
    public CellTable create(ColumnHeaderType type, Integer mergeLines, Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue) {
        switch (type) {
            case STRING_NUMBER -> {return mapper.mapToCell(mergeLines, columnHeaders.get(String.valueOf(type)), stringSequentialNumber, valid(cellValue));}
            case DATE, SURVEYS_DESCRIPTION, ORGANIZATION_NAME, REPAIR_DESCRIPTION, DOCUMENT_NUMBER -> {return mapper.mapToCell(mergeLines, columnHeaders.get(String.valueOf(type)), stringSequentialNumber, valid(cellValue));}
            case ELEMENT, PART_ELEMENT -> {return mapper.mapToCell(mergeLines, columnHeaders.get(String.valueOf(type)), stringSequentialNumber, valid(cellValue));}
            case DEFECTS,
            case REPAIR_ELEMENT,
            case VISUAL_INSPECTION,
            case DESIGN_THICKNESS,
            case MEASURED_THICKNESS,
            case MAX_CORROSION,
            case RESIDUAL_THICKNESS,
            case MIN_ALLOWABLE_THICKNESS,
            case PLACE_NUMBER,
            case HEIGHT,
            case DEVIATION,
            case PRECIPITATION,
            case DIFFERENCE_NEIGHBORING_POINTS,
            case DIFFERENCE_DIAMETRICAL_POINTS,
            case DIAMETER,
            case HARDNESS;
        }
    }

    public CellTable getCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue) {
        return mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.REPAIR_DESCRIPTION)), stringSequentialNumber, valid(cellValue));
    }

    private String valid(String cellValue) {
        if (cellValue == null) {
            return DASH;
        }
        return cellValue;
    }
}
