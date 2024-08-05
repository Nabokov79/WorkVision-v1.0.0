package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.List;

@Service
public class MeasurementHardnessCellCreateServiceImpl implements MeasurementHardnessCellCreateService {

    @Override
    public List<CellTable> create(DocumentTable table, TableTemplate template, WorkJournalDto journal) {
        Set<HardnessMeasurement> measurements = hardnessMeasurementService.getAllByIds(surveyJournal.getId()
                , surveyJournal.getEquipmentId());
        Map<String, Integer> columnHeaders = tableTemplate.getColumnHeaders().stream()
                .collect(Collectors.toMap(ColumnHeaderTemplate::getColumnHeaderType
                        , ColumnHeaderTemplate::getSequentialNumber));
        int stringSequentialNumber = 1;
        List<CellTable> cells = new ArrayList<>();
        for (HardnessMeasurement measurement : measurements) {
            cells.add(cellFactoryService.createStringNumberCell(columnHeaders, stringSequentialNumber, table));
            cells.add(cellFactoryService.createElementCell(columnHeaders
                    , 0
                    , stringSequentialNumber
                    , measurement.getElementName()
                    , table));
            if (measurement.getDiameter() != null) {
                cells.add(cellFactoryService.createDiameterCell(columnHeaders
                        , stringSequentialNumber
                        , String.valueOf(measurement.getDiameter())
                        , table));
            }
            if (measurement.getMeasurementNumber() != null) {
                cells.add(cellFactoryService.createPlaceNumberCell(columnHeaders
                        , stringSequentialNumber
                        , String.valueOf(measurement.getMeasurementNumber())
                        , table));
            }
            cells.add(cellFactoryService.createHardnessCell(columnHeaders
                    , stringSequentialNumber
                    , String.valueOf(measurement.getMeasurementValue())
                    , table));
            stringSequentialNumber++;
        }
        return cells;
    }
}