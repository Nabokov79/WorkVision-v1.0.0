package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.DeviationYear;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;

import java.util.List;

public interface CalculationGeodesicMeasurementService {

    Integer getMinMeasurement(List<Integer> calculatedHeights);

    Integer getDeviation(Integer min, Integer calculatedHeight);

    Integer getPrecipitation(Integer newDeviation, List<DeviationYear> deviationYears);

    List<GeodesicMeasurementsPoint> recalculateByTransition(List<GeodesicMeasurementsPoint> measurements);

    Long getEquipmentId(List<GeodesicMeasurementsPoint> measurements);

    int getMeasurementNumber(int measurementNumber);
}