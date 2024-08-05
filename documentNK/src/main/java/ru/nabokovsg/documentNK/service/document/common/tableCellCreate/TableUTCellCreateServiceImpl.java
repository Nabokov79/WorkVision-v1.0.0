package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.List;

@Service
public class TableUTCellCreateServiceImpl implements TableUTCellCreateService {

    @Override
    public List<CellTable> create(DocumentTable table, TableTemplate template, WorkJournalDto journal) {
        Set<ResultUltrasonicThicknessMeasurement> measurements = utMeasurementService.getAllByIds(surveyJournal.getId()
                , surveyJournal.getEquipmentId());
        Map<String, Integer> columnHeaders = tableTemplate.getColumnHeaders().stream()
                .collect(Collectors.toMap(ColumnHeaderTemplate::getColumnHeaderType
                        , ColumnHeaderTemplate::getSequentialNumber));
        int stringSequentialNumber = 1;
        List<CellTable> cells = new ArrayList<>();
        Map<String, Integer> mergeLines = new HashMap<>();
        measurements.forEach(m -> mergeLines.merge(m.getElementName(), 1, Integer::sum));
        for (ResultUltrasonicThicknessMeasurement measurement : measurements) {
            cells.add(cellFactoryService.createStringNumberCell(columnHeaders, stringSequentialNumber, table));
            cells.add(cellFactoryService.createElementCell(columnHeaders
                    , mergeLines.get(measurement.getElementName())
                    , stringSequentialNumber
                    , measurement.getElementName()
                    , table));
            if (measurement.getDiameter() != null) {
                cells.add(cellFactoryService.createDiameterCell(columnHeaders
                        , stringSequentialNumber
                        , String.valueOf(measurement.getDiameter())
                        , table));
            }
            cells.add(cellFactoryService.createMeasuredThicknessCell(columnHeaders
                    , stringSequentialNumber
                    , String.join(" - ", String.valueOf(measurement.getMinMeasurementValue())
                            , String.valueOf(measurement.getMaxMeasurementValue()))
                    , table));
            cells.add(cellFactoryService.createMaxCorrosionCell(columnHeaders
                    , stringSequentialNumber
                    , String.valueOf(measurement.getMaxCorrosion())
                    , table));
            cells.add(cellFactoryService.createMinAllowableThicknessCell(columnHeaders
                    , stringSequentialNumber
                    , String.valueOf(measurement.getMinAcceptableValue())
                    , table));
            if (measurement.getMeasurementNumber() != null) {
                cells.add(cellFactoryService.createPlaceNumberCell(columnHeaders
                        , stringSequentialNumber
                        , String.valueOf(measurement.getMeasurementNumber())
                        , table));
            }
            if (measurement.getPartElementId() != null) {
                cells.add(cellFactoryService.createPartElementCell(columnHeaders
                        , stringSequentialNumber
                        , measurement.getElementName()
                        , table));
            }
            stringSequentialNumber++;
        }
        return cells;
    }
}