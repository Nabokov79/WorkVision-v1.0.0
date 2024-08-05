package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.client.DocumentNKClient;
import ru.nabokovsg.documentNK.dto.client.documentCreate.*;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderType;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableVMSCellCreateServiceImpl implements TableVMSCellCreateService {

    private final DocumentNKClient client;
    private final static String NO_DEFECT = "Дефекты не обнаружены";
    private final static String MISSING = "отсутствуют";

    @Override
    public List<CellTable> create(DocumentTable table, TableTemplate template, WorkJournalDto journal) {
        List<VisualMeasuringSurveyDto> visualMeasuringSurveys = client.getVisualMeasuringSurvey(journal.getEquipmentId());
        Map<String, Integer> columnHeaders = template.getColumnHeaders().stream()
                .collect(Collectors.toMap(ColumnHeaderTemplate::getColumnHeaderType
                        , ColumnHeaderTemplate::getSequentialNumber));
        List<CellTable> cells = new ArrayList<>();
        Map<String, Integer> mergeLines = new HashMap<>();
        Map<String, Set<IdentifiedDefectDto>> defectsMeasurement = new HashMap<>();
        Map<String, Set<CompletedRepairDto>> repairElements = new HashMap<>();
        Map<String, String> visualInspections = new HashMap<>();
        Map<String, String> names = new HashMap<>();
        for (VisualMeasuringSurveyDto vms : visualMeasuringSurveys) {
            if (!vms.getExaminedPartElements().isEmpty()) {
                vms.getExaminedPartElements().forEach(part -> {
                    add(part.getPartElementName(), defectsMeasurement, repairElements, visualInspections, part.getIdentifiedDefects(), part.getCompletedRepairs(), part.getInspection());
                    names.put(vms.getElementName(), part.getPartElementName());
                    mergeLines.put(part.getPartElementName(), part.getIdentifiedDefects().size());
                });
            } else {
                add(vms.getElementName(), defectsMeasurement, repairElements, visualInspections,  vms.getIdentifiedDefects(), vms.getCompletedRepairs(), vms.getInspection());
                names.put(vms.getElementName(), null);
                mergeLines.put(vms.getElementName(), vms.getIdentifiedDefects().size());
            }
        }
        names.forEach((k, v) -> {

        });
        return cells;
    }

    private void add(String name, Map<String, Set<IdentifiedDefectDto>> defectsMeasurement, Map<String, Set<CompletedRepairDto>> repairElements, Map<String, String> visualInspections, Set<IdentifiedDefectDto> identifiedDefects, Set<CompletedRepairDto> completedRepairs, String inspection) {
        defectsMeasurement.put(name, identifiedDefects);
        repairElements.put(name, completedRepairs);
        visualInspections.put(name, inspection);
    }

    private void createByIdentifiedDefects(List<CellTable> cells, String elementName, String partElementName, int stringSequentialNumber, int mergeLines, Set<IdentifiedDefectDto> identifiedDefects) {
        for (IdentifiedDefectDto defect : identifiedDefects) {
            cells.add(cellFactoryService.createElementCell(elementName
                    , mergeLines
                    , stringSequentialNumber));
            if (partElementName != null) {
                cells.add(cellFactoryService.createElementCell(partElementName
                        , mergeLines
                        , stringSequentialNumber));
            }
            cells.addAll(cellFactoryService.createDefectCell(columnHeaders
                    , defect
                    , stringSequentialNumber));

            stringSequentialNumber += mergeLines;
        }
    }

    @Override
    public CellTable createElementCell(Map<String, Integer> columnHeaders, Integer mergeLines, int stringSequentialNumber, String cellValue) {
        return mapper.mapToElementCell(mergeLines, columnHeaders.get(String.valueOf(ColumnHeaderType.ELEMENT)), stringSequentialNumber, valid(cellValue));
    }

    @Override
    public CellTable createPartElementCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue) {
        return mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.PART_ELEMENT)), stringSequentialNumber, valid(cellValue));
    }

    public List<CellTable> createDefectCell(List<CellTable> cells, Map<String, Integer> columnHeaders, Set<DefectMeasurement> defects, int stringSequentialNumber, DocumentTable table) {
        List<CellTable> cells = new ArrayList<>();
        if (defects != null) {
            for(DefectMeasurement defect : defects) {
                cells.add(mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.DEFECTS)), stringSequentialNumber, String.join("", defect.getDefectName(), ":"), table));
                stringSequentialNumber += 1;
                cells.addAll(createCalculationParameter(columnHeaders.get(String.valueOf(ColumnHeaderType.DEFECTS)), defect.getParameterMeasurements(), stringSequentialNumber, table));
            }
        } else {
            cells.add(mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.DEFECTS)), stringSequentialNumber, NO_DEFECT, table));
        }

        return cells;
    }

    private List<CellTable> createCalculationParameter(Integer columnSequentialNumber, Set<ParameterMeasurementDto> parameters, int stringSequentialNumber, DocumentTable table) {
        List<CellTable> cells = new ArrayList<>(parameters.size());
        int number;
        Map<Integer, Set<ParameterMeasurementDto>> parameterMeasurements = new HashMap<>();
        parameters.forEach( p -> {
            Set<ParameterMeasurementDto> parametersMeasurements = parameterMeasurements.get(p.getMeasurementNumber());
            if (parametersMeasurements.isEmpty()) {
                parameterMeasurements.put(p.getMeasurementNumber(), Set.of(p));
            } else {
                parametersMeasurements.add(p);
                parameterMeasurements.put(p.getMeasurementNumber(), parametersMeasurements);
            }
        });
        for (int i = 1; i <= parameterMeasurements.size(); i++) {
            Set<ParameterMeasurementDto> parametersMeasurements = parameterMeasurements.get(i);
        }
        for (ParameterMeasurementDto param : parameters) {
            number = param.getParameterNumber();
            if (number == param.getParameterNumber()) {
                if (param.getMaxValue() == null) {
                    cells.add(mapper.mapToCellTable(columnSequentialNumber, stringSequentialNumber, String.join(" ", param.getParameterName(), "до", String.valueOf(param.getMaxValue()), param.getUnitMeasurement()), table));
                } else {
                    cells.add(mapper.mapToCellTable(columnSequentialNumber, stringSequentialNumber, String.join(" ", param.getParameterName(), "от", String.valueOf(param.getMinValue()), "до", String.valueOf(param.getMaxValue()), param.getUnitMeasurement()), table));
                }
            } else {
                String paramNumber = String.join("", String.valueOf(param.getParameterNumber()), ")");
                if (param.getMaxValue() == null) {
                    cells.add(mapper.mapToCellTable(columnSequentialNumber, stringSequentialNumber, String.join(" ", paramNumber, param.getParameterName(), "до", String.valueOf(param.getMinValue()), param.getUnitMeasurement()), table));
                } else {
                    cells.add(mapper.mapToCellTable(columnSequentialNumber, stringSequentialNumber, String.join(" ", paramNumber, param.getParameterName(), "от", String.valueOf(param.getMinValue()), "до", String.valueOf(param.getMaxValue()), param.getUnitMeasurement()), table));
                }
            }
        }
        return cells;
    }

    private List<CellTable> createRepairElementCell(Map<String, Integer> columnHeaders, Set<CompletedRepairElement> repairElements, int stringSequentialNumber, DocumentTable table) {
        List<CellTable> cells = new ArrayList<>();
        if (repairElements != null) {
            for(CompletedRepairElement repair : repairElements) {
                cells.add(mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.REPAIR_ELEMENT)), stringSequentialNumber, String.join("", repair.getRepairName(), ":"), table));
                stringSequentialNumber += 1;
                cells.addAll(createCalculationParameter(columnHeaders.get(String.valueOf(ColumnHeaderType.REPAIR_ELEMENT)), repair.getParameterMeasurements(), stringSequentialNumber, table));
            }
        } else {
            cells.add(mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.REPAIR_ELEMENT)), stringSequentialNumber, MISSING, table));
        }
        return cells;
    }

    private CellTable createVisualInspectionCell(Map<String, Integer> columnHeaders, int stringSequentialNumber, String cellValue, DocumentTable table) {
        if (cellValue == null) {
            return mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.VISUAL_INSPECTION)), stringSequentialNumber, MISSING, table);
        }
        return mapper.mapToCellTable(columnHeaders.get(String.valueOf(ColumnHeaderType.VISUAL_INSPECTION)), stringSequentialNumber, valid(cellValue), table);
    }
}