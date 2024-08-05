package ru.nabokovsg.documentNK.service.document.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.documentNK.mapper.document.common.CellTableMapper;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableType;
import ru.nabokovsg.documentNK.repository.document.common.CellTableRepository;
import ru.nabokovsg.documentNK.service.document.common.tableCellCreate.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CellTableServiceImpl implements CellTableService {

    private final CellTableRepository repository;
    private final CellTableMapper mapper;
    private final SurveysTableCellCreateService surveysTableCellCreateService;
    private final GeodesicMeasurementsCellCreateService geodesicMeasurementsCellCreateService;
    private final TableUCCellCreateService tableUCCellCreateService;
    private final MeasurementHardnessCellCreateService measurementHardnessCellCreateService;
    private final RepairTableCellCreateService repairTableCellCreateService;
    private final TableVMSCellCreateService tableVMSCellCreateService;
    private final TableVMCCellCreateService tableVMCCellCreateService;
    private final TableUTCellCreateService tableUTCellCreateService;
    private final DocumentNKClient client;

    @Override
    public void save(DocumentTable table, List<CellTable> cells) {
        repository.saveAll(cells.stream().map(c -> mapper.mapToCellTable(c, table)).toList());
    }

    private List<CellTable> common(Set<TableTemplate> templates, Map<String, DocumentTable> tables, String tableType, Long equipmentId) {
        List<CellTable> cells = new ArrayList<>();
        Map<String, TableTemplate> tableTemplates = templates.stream().collect(Collectors.toMap(TableTemplate::getTableType, t -> t));
        TableType type = TableType.valueOf(tableType);
        switch (type) {
            case SURVEYS_TABLE -> {
                return surveysTableCellCreateService.create(template, journal);
            }
            case REPAIR_TABLE -> {
                return repairTableCellCreateService.create(template, journal);
            }
            case TABLE_VMS -> {
                return tableVMSCellCreateService.create(template, journal);
            }
            case TABLE_VMC -> {
                return tableVMCCellCreateService.create(template, journal);
            }
            case TABLE_UT -> {
                return tableUTCellCreateService.create(template, journal);
            }
            case MEASUREMENT_RP -> {
                EquipmentGeodesicMeasurementsDto measurements = client.getEquipmentGeodesicMeasurements(equipmentId);
                TableTemplate referencePointTable = tableTemplates.get(String.valueOf(TableType.MEASUREMENT_RP));
                cells.addAll(geodesicMeasurementsCellCreateService.createByReferencePoint(measurements.getReferencePoints(),referencePointTable.getColumnHeaders()).stream().map(c -> mapper.mapToCellTable(c, tables.get(referencePointTable.getTableName()))).toList());
                return cells;
            }
            case MEASUREMENT_CP -> {
                EquipmentGeodesicMeasurementsDto measurements = client.getEquipmentGeodesicMeasurements(equipmentId);
                TableTemplate controlPoint = tableTemplates.get(String.valueOf(TableType.MEASUREMENT_CP));
                cells.addAll(geodesicMeasurementsCellCreateService.createByControlPoint(measurements.getControlPoints(), measurements.getPointDifferences(), controlPoint.getColumnHeaders()).stream().map(c -> mapper.mapToCellTable(c, tables.get(controlPoint.getTableName()))).toList());
                return cells;
            }
            case TABLE_UC -> {
                return tableUCCellCreateService.create(template, journal);
            }
            case MEASUREMENT_HARDNESS -> {
                return measurementHardnessCellCreateService.create(template, journal);
            }
        }
    }
}