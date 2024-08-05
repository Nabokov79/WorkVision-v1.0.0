package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.*;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GeodesicMeasurementsCellCreateServiceImpl implements GeodesicMeasurementsCellCreateService {

    @Override
    public List<CellTable> createByReferencePoint(Set<ReferencePointDto> referencePoints,  Set<ColumnHeaderTemplate> templates) {
        Map<Integer, ReferencePointDto> points = referencePoints
                .stream()
                .collect(Collectors.toMap(ReferencePointDto::getPlaceNumber, r -> r));
        List<CellTable> cells = new ArrayList<>();
        for (int i = 1; i <= points.size(); i++) {
            ReferencePointDto point = points.get(i);
            cells.add(cellFactoryService.createPlaceNumberCell(columnHeaders
                    , i
                    , String.valueOf(point.getPlaceNumber())
                    , table));
            cells.add(cellFactoryService.createHeightCell(columnHeaders
                    , i
                    , String.valueOf(point.getCalculatedHeight())
                    , table));
            cells.add(cellFactoryService.createDeviationCell(columnHeaders
                    , i
                    , String.valueOf(point.getDeviation())
                    , table));
            int finalI = i;
            point.getDeviationYeas().stream()
                    .sorted(Comparator.comparing(DeviationYearDto::getYear))
                    .forEach(d -> cells.add(cellFactoryService.createDeviationCell(columnHeaders
                            , finalI
                            , String.valueOf(d.getDeviation())
                            , table)));

        }
        return cells;
    }

    @Override
    public List<CellTable> createByControlPoint(Set<ControlPointDto> controlPoints, Set<PointDifferenceDto> pointDifferences, Set<ColumnHeaderTemplate> templates) {

    }
}