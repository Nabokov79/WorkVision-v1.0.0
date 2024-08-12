package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.DeviationYear;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalculationGeodesicMeasurementServiceImpl implements CalculationGeodesicMeasurementService {

    @Override
    public Integer getMinMeasurement(List<Integer> calculatedHeights) {
        Optional<Integer> min = calculatedHeights.stream()
                .filter(Objects::nonNull)
                .min(Integer::compareTo);
        if (min.isEmpty()) {
            return 0;
        } else {
            return min.get();
        }
    }

    @Override
    public Integer getDeviation(Integer min, Integer calculatedHeight) {
        return min - calculatedHeight;
    }

    @Override
    public Integer getPrecipitation(Integer newDeviation, List<DeviationYear> deviationYears) {
        if (deviationYears == null) {
            return 0;
        }
        Map<Integer, Integer> deviationYeasDb = deviationYears.stream()
                .collect(Collectors.toMap(DeviationYear::getYear
                        , DeviationYear::getDeviation));
        return newDeviation - deviationYeasDb.get(deviationYeasDb.keySet()
                .stream()
                .mapToInt(d -> d)
                .max()
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<GeodesicMeasurementsPoint> recalculateByTransition(List<GeodesicMeasurementsPoint> measurements) {
        int delta = 0;
        Map<Integer, GeodesicMeasurementsPoint> recalculateMeasurements = measurements.stream()
                .collect(Collectors.toMap(GeodesicMeasurementsPoint::getSequentialNumber, g -> g));
        for (int i = 1;  i < measurements.size() + 1; i++) {
            GeodesicMeasurementsPoint measurement = recalculateMeasurements.get(i);
            recalculateMeasurements.put(measurement.getSequentialNumber(), getRecalculateMeasurement(measurement, delta));
            if (measurement.getTransitionValue() != null) {
                delta = getDelta(measurement.getControlPointValue(), measurement.getTransitionValue());
            }
        }
        return new ArrayList<>(recalculateMeasurements.values());
    }

    @Override
    public Long getEquipmentId(List<GeodesicMeasurementsPoint> measurements) {
        List<Long> ids = measurements.stream().map(GeodesicMeasurementsPoint::getEquipmentId).distinct().toList();
        if (ids.size() > 1) {
            throw new BadRequestException(String.format("There are more than one IDs=%s", ids));
        }
        return ids.get(0);
    }

    private GeodesicMeasurementsPoint getRecalculateMeasurement(GeodesicMeasurementsPoint measurement, int delta) {
        if (measurement.getReferencePointValue() != null) {
            measurement.setReferencePointValue(
                    getSumMeasurementAndDelta(measurement.getReferencePointValue(), delta)
            );
        }
        measurement.setControlPointValue(getSumMeasurementAndDelta(measurement.getControlPointValue(), delta));
        return measurement;
    }

    private Integer getDelta(int measurementValue, int transitionValue) {
        return measurementValue - transitionValue;
    }

    private Integer getSumMeasurementAndDelta(int measurementValue, int delta) {
        return measurementValue + delta;
    }
}