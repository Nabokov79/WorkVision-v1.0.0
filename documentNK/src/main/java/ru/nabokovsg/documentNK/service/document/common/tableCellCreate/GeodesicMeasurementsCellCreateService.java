package ru.nabokovsg.documentNK.service.document.common.tableCellCreate;

import ru.nabokovsg.documentNK.dto.client.documentCreate.*;
import ru.nabokovsg.documentNK.model.document.common.CellTable;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GeodesicMeasurementsCellCreateService {

   List<CellTable> createByReferencePoint(Set<ReferencePointDto> referencePoints, Set<ColumnHeaderTemplate> templates);

    List<CellTable> createByControlPoint(Set<ControlPointDto> controlPoints, Set<PointDifferenceDto> pointDifferences, Set<ColumnHeaderTemplate> templates);
}