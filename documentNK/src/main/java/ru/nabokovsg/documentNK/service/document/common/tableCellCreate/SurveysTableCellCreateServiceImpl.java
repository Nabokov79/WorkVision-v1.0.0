package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentInspectionDto;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderType;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveysTableCellCreateServiceImpl implements SurveysTableCellCreateService {

    private final DocumentNKClient client;
    private final CellFactoryService service;

    @Override
    public List<CellTable> create(TableTemplate template, Long equipmentId) {
        List<EquipmentInspectionDto> inspections = client.getEquipmentInspections(equipmentId);
        if(!inspections.isEmpty()) {
            inspections = inspections.stream().sorted(Comparator.comparing(EquipmentInspectionDto::getDate)).toList();
        }
        Map<String, Integer> columnHeaders = template.getColumnHeaders()
                .stream()
                .collect(Collectors.toMap(ColumnHeaderTemplate::getColumnHeaderType
                        , ColumnHeaderTemplate::getSequentialNumber));
        List<CellTable> cells = new ArrayList<>();
        int stringSequentialNumber = 1;
        Integer mergeLines = 0;
        for (EquipmentInspectionDto i : inspections) {
            cells.add(service.create(ColumnHeaderType.DATE, mergeLines, columnHeaders, stringSequentialNumber, i.getDate()));
            cells.add(service.create(ColumnHeaderType.SURVEYS_DESCRIPTION, mergeLines, columnHeaders, stringSequentialNumber, i.getInspection()));
            cells.add(service.create(ColumnHeaderType.DOCUMENT_NUMBER, mergeLines, columnHeaders, stringSequentialNumber, i.getDocumentNumber()));
            cells.add(service.create(ColumnHeaderType.ORGANIZATION_NAME, mergeLines, columnHeaders, stringSequentialNumber, i.getOrganization()));
            stringSequentialNumber++;
        }
        return cells;
    }
}