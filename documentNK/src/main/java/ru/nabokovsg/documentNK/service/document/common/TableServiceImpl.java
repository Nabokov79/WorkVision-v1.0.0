package ru.nabokovsg.documentNK.service.document.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.dto.client.documentCreate.EquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.documentNK.mapper.document.common.TableMapper;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableType;
import ru.nabokovsg.documentNK.repository.document.common.TableRepository;
import ru.nabokovsg.documentNK.service.document.common.tableCellCreate.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository repository;
    private final TableMapper mapper;
    private final DocumentNKClient client;
    private final CellTableService cellTableService;
    private final SurveysTableCellCreateService surveysTableCellCreateService;
    private final RepairTableCellCreateService repairTableCellCreateService;
    private final GeodesicMeasurementsCellCreateService geodesicMeasurementsCellCreateService;
    private final TableVMCCellCreateService tableVMCCellCreateService;
    private final TableUCCellCreateService tableUCCellCreateService;

    @Override
    public DocumentTable saveForSubsection(TableTemplate tableTemplate, DocumentCreationDataDto documentDataDto) {
        DocumentTable table = repository.save(mapper.mapToDocumentTable(tableTemplate));
        TableType type = TableType.valueOf(tableTemplate.getTableType());
        switch (type) {
            case SURVEYS_TABLE ->
                    cellTableService.save(table, surveysTableCellCreateService.create(tableTemplate, documentDataDto.getEquipmentId()));
            case REPAIR_TABLE ->
                    cellTableService.save(table, repairTableCellCreateService.create(tableTemplate, documentDataDto.getEquipmentId()));
        }
        return table;
    }

    @Override
    public void saveForProtocolReport(ProtocolReport protocol, Set<TableTemplate> templates,DocumentCreationDataDto documentDataDto) {
        createCells(templates
                , save(templates.stream()
                        .map(t -> mapper.mapWithProtocolReport(t, protocol))
                        .toList())
                        .stream()
                        .collect(Collectors.toMap(DocumentTable::getTableType, t -> t))
                , documentDataDto.getEquipmentId());
    }

    @Override
    public void saveForProtocolControl(ProtocolControl protocol, TableTemplate template, DocumentCreationDataDto documentDataDto) {
        TableType type = TableType.valueOf(template.getTableType());
        DocumentTable table = repository.save(mapper.mapWithProtocolControl(template, protocol));
        switch (type) {
            case TABLE_VMC -> {
                return tableVMCCellCreateService.create(table, template, documentDataDto);
            }
            case TABLE_UC -> {
                return tableUCCellCreateService.create(table, template, documentDataDto);
            }
        }
    }

    @Override
    public void saveForSurveyProtocol(SurveyProtocol protocol, Set<TableTemplate> templates, DocumentCreationDataDto documentDataDto) {
        createCells(templates
                , save(templates.stream()
                        .map(t -> mapper.mapWithSurveyProtocol(t, protocol))
                        .toList())
                        .stream()
                        .collect(Collectors.toMap(DocumentTable::getTableType, t -> t))
                , journal.getEquipmentId());

    }

    private List<DocumentTable> save(List<DocumentTable> tables) {
        return repository.saveAll(tables);
    }

    private List<CellTable> createCells(Set<TableTemplate> templates, Map<String, DocumentTable> tables, Long equipmentId) {
        Map<String, TableTemplate> tableTemplates = templates.stream().collect(Collectors.toMap(TableTemplate::getTableType, t -> t));
        TableType type = TableType.valueOf(templates.stream().map(TableTemplate::getTableType).toList().get(0));
        switch (type) {
            case TABLE_VMS -> {
                return tableVMSCellCreateService.create(template, journal);
            }
            case TABLE_UT -> {
                return tableUTCellCreateService.create(template, journal);
            }
            case MEASUREMENT_RP, MEASUREMENT_CP -> {
                EquipmentGeodesicMeasurementsDto measurements = client.getEquipmentGeodesicMeasurements(equipmentId);
                cellTableService.save(tables.get("MEASUREMENT_RP"), geodesicMeasurementsCellCreateService.createByReferencePoint(measurements.getReferencePoints(), tableTemplates.get(String.valueOf(TableType.MEASUREMENT_RP)).getColumnHeaders()));
                cellTableService.save(tables.get("MEASUREMENT_CP"), geodesicMeasurementsCellCreateService.createByControlPoint(measurements.getControlPoints(), measurements.getPointDifferences(), tableTemplates.get(String.valueOf(TableType.MEASUREMENT_CP)).getColumnHeaders()));
            }
            case MEASUREMENT_HARDNESS -> {
                return measurementHardnessCellCreateService.create(template, journal);
            }
        }
    }
}