package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy.PointDifferenceMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.enums.GeodesicPointType;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.*;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableDeviationsGeodesy;
import ru.nabokovsg.diagnosedNK.repository.measurement.geodesy.PointDifferenceRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.common.QueryDSLRequestService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PointDifferenceServiceImpl implements PointDifferenceService {

    private final PointDifferenceRepository repository;
    private final PointDifferenceMapper mapper;
    private final CalculationGeodesicMeasurementService calculationService;
    private final ControlPointMeasurementService controlPointMeasurementService;
    private final EquipmentGeodesicMeasurementsService equipmentGeodesicMeasurementsService;
    private final QueryDSLRequestService requestService;

    @Override
    public void save(AcceptableDeviationsGeodesy acceptableDeviationsGeodesy
                   , List<GeodesicMeasurementsPoint> measurements
                   , EquipmentGeodesicMeasurements geodesicMeasurements) {
        Long equipmentId = calculationService.getEquipmentId(measurements);
        Set<PointDifference> pointDifferences = requestService.getAllPointDifference(equipmentId);
        if (!pointDifferences.isEmpty() && measurements.size() == pointDifferences.size()) {
            update(acceptableDeviationsGeodesy, pointDifferences, measurements);
            return;
        }
        Set<ControlPoint> controlPoints = controlPointMeasurementService.save(measurements, geodesicMeasurements);
        pointDifferences = new HashSet<>(repository.saveAll(create(acceptableDeviationsGeodesy, controlPoints)
                .stream()
                        .map(m -> mapper.mapPointDifferenceWithEquipmentGeodesicMeasurements(m, geodesicMeasurements))
                .toList()));
        equipmentGeodesicMeasurementsService.addControlPointAndPointDifference(equipmentId, controlPoints, pointDifferences);
    }

    public void update(AcceptableDeviationsGeodesy acceptableDeviationsGeodesy
                     , Set<PointDifference> pointDifferences
                     , List<GeodesicMeasurementsPoint> measurements) {
        Map<Integer,PointDifference> pointDifferencesDb = pointDifferences.stream()
                                               .collect(Collectors.toMap(PointDifference::getFirstPlaceNumber, p -> p));
        EquipmentGeodesicMeasurements geodesicMeasurements = new EquipmentGeodesicMeasurements();
        repository.saveAll(create(acceptableDeviationsGeodesy, controlPointMeasurementService.save(measurements, geodesicMeasurements))
                .stream()
                .map(p -> mapper.mapToUpdatePointDifference(pointDifferencesDb.get(p.getFirstPlaceNumber()), p))
                .toList());
    }

    private List<PointDifference> create(AcceptableDeviationsGeodesy acceptableDeviationsGeodesy
                                       , Set<ControlPoint> controlPoints) {
        Map<Integer, ControlPoint> points = controlPoints.stream()
                                                       .collect(Collectors.toMap(ControlPoint::getPlaceNumber, c -> c));
        return Stream.of(calculatedNeighboringPoints(acceptableDeviationsGeodesy, points)
                       , calculatedDiametricalPoints(acceptableDeviationsGeodesy, points))
                     .flatMap(Collection::stream)
                     .toList();
    }

    private List<PointDifference> calculatedNeighboringPoints(AcceptableDeviationsGeodesy acceptableDeviationsGeodesy
                                                            , Map<Integer, ControlPoint> points) {
        Map<Integer, Integer> neighboringPoints = points.keySet()
                                                        .stream()
                                                        .collect(Collectors.toMap(p -> p
                                                                        , p -> getNeighboringPoints(p, points.size())));
        return neighboringPoints.keySet()
                                .stream()
                                .map(p -> mapper.mapToPointDifference(GeodesicPointType.NEIGHBORING
                                                , points.get(p)
                                                , p
                                                , points.get(neighboringPoints.get(p)).getPlaceNumber()
                                                , Math.abs(calculationService.getDeviation(
                                                                  points.get(p).getDeviation()
                                                                , points.get(neighboringPoints.get(p)).getDeviation())))
                                )
                                .map(p -> determinePermissibleDeviation(p, acceptableDeviationsGeodesy))
                                .toList();
    }

    private Integer getNeighboringPoints(Integer firstPlace, int size) {
        if (firstPlace != size) {
            return ++firstPlace;
        }
       return 1;
    }

    private List<PointDifference> calculatedDiametricalPoints(AcceptableDeviationsGeodesy acceptableDeviationsGeodesy
                                                            , Map<Integer, ControlPoint> points) {
        int difference = (int) Math.floor(points.keySet().stream().max(Integer::compare).orElse(0) / 2.0);
        if (difference == 0) {
            throw new RuntimeException(
                    String.format("Error in calculating the diametric points, difference=%s", difference)
            );
        }
        Map<Integer, Integer> diametricalPoints = points.keySet().stream()
                                                                .filter(i -> i <= difference)
                                                                .collect(Collectors.toMap(i -> i, i -> i + difference));
        return diametricalPoints.keySet()
                                .stream()
                                .map(p -> mapper.mapToPointDifference(GeodesicPointType.DIAMETRICAL
                                        , points.get(p)
                                        , p
                                        , points.get(diametricalPoints.get(p)).getPlaceNumber()
                                        , Math.abs(calculationService.getDeviation(
                                                                  points.get(p).getDeviation()
                                                                , points.get(diametricalPoints.get(p)).getDeviation())))
                                )
                .map(p -> determinePermissibleDeviation(p, acceptableDeviationsGeodesy))
                                .toList();
    }

    private PointDifference determinePermissibleDeviation(PointDifference pointDifference
                                                         , AcceptableDeviationsGeodesy acceptableDeviationsGeodesy) {
        switch (pointDifference.getGeodesicPointType()) {
            case DIAMETRICAL -> {
                return mapper.mapPointDifferenceWithControlPointMeasurement(pointDifference
                   , (pointDifference.getDifference() > acceptableDeviationsGeodesy.getMaxDifferenceDiametricPoints()));
            }
            case NEIGHBORING -> {
                return mapper.mapPointDifferenceWithControlPointMeasurement(pointDifference
                 , (pointDifference.getDifference() > acceptableDeviationsGeodesy.getMaxDifferenceNeighboringPoints()));
            }
            default -> {
                return pointDifference;
            }
        }
    }
}