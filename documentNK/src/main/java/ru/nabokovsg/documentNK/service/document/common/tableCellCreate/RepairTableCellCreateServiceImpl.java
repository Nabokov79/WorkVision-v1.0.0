package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentRepairDto;
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
public class RepairTableCellCreateServiceImpl implements RepairTableCellCreateService {

    private final DocumentNKClient client;
    private final CellFactoryService service;

    @Override
    public List<CellTable> create(TableTemplate template, Long equipmentId) {
        List<EquipmentRepairDto> repairs = client.getEquipmentRepairs(equipmentId);
        if (!repairs.isEmpty()) {
            repairs = repairs.stream().sorted(Comparator.comparing(EquipmentRepairDto::getDate)).toList();
        }
        Map<String, Integer> columnHeaders = template.getColumnHeaders().stream()
                .collect(Collectors.toMap(ColumnHeaderTemplate::getColumnHeaderType
                        , ColumnHeaderTemplate::getSequentialNumber));
        List<CellTable> cells = new ArrayList<>();
        int stringSequentialNumber = 1;
        Integer mergeLines = 0;
        for (EquipmentRepairDto i : repairs) {
            cells.add(service.create(ColumnHeaderType.DATE, mergeLines, columnHeaders, stringSequentialNumber, i.getDate()));
            cells.add(service.create(ColumnHeaderType.REPAIR_DESCRIPTION, mergeLines, columnHeaders, stringSequentialNumber, i.getDescription()));
            cells.add(service.create(ColumnHeaderType.ORGANIZATION_NAME, mergeLines, columnHeaders, stringSequentialNumber, i.getOrganization()));
            stringSequentialNumber++;
        }
        return cells;
    }
}