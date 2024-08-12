package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy.ControlPointMeasurementMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;
import ru.nabokovsg.diagnosedNK.repository.measurement.geodesy.ControlPointMeasurementRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.QueryDSLRequestService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ControlPointMeasurementServiceImpl implements ControlPointMeasurementService {

    private final ControlPointMeasurementRepository repository;
    private final ControlPointMeasurementMapper mapper;
    private final CalculationGeodesicMeasurementService calculationService;
    private final QueryDSLRequestService requestService;

    @Override
    public Set<ControlPoint> save(List<GeodesicMeasurementsPoint> measurements
                                , EquipmentGeodesicMeasurements geodesicMeasurements) {
        Integer min = getMin(measurements);
        Set<ControlPoint> controlPoints = requestService.getAllControlPoint(calculationService.getEquipmentId(measurements));
        if (!controlPoints.isEmpty() && controlPoints.size() == measurements.size()) {
            return update(controlPoints, min, measurements);
        }
        return new HashSet<>(repository.saveAll(measurements.stream()
                                                    .map(g -> mapper.mapToControlPoint(
                                                            g
                                                            , calculationService.getDeviation(min
                                                                                           , g.getControlPointValue())
                                                    , geodesicMeasurements))
                                                    .toList()));
    }

    private Set<ControlPoint> update(Set<ControlPoint> controlPoints
            , Integer min, List<GeodesicMeasurementsPoint> measurements) {
        Map<Integer, ControlPoint> points = controlPoints
                                                      .stream()
                                                      .collect(Collectors.toMap(ControlPoint::getPlaceNumber, c -> c));
        return new HashSet<>(repository.saveAll(measurements.stream()
                                                            .map(g -> mapper.mapToUpdateControlPoint(
                                                                    points.get(g.getNumberMeasurementLocation())
                                                                    , g
                                                                    , calculationService.getDeviation(min
                                                                            , g.getControlPointValue())))
                                                            .toList()));
    }

    private int getMin(List<GeodesicMeasurementsPoint> measurements) {
        return calculationService.getMinMeasurement(measurements.stream()
                .map(GeodesicMeasurementsPoint::getControlPointValue)
                .toList());
    }
}