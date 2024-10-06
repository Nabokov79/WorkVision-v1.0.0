package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy.ReferencePointMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableDeviationsGeodesy;
import ru.nabokovsg.diagnosedNK.repository.measurement.geodesy.ReferencePointRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.QueryDSLRequestService;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferencePointMeasurementServiceImpl implements ReferencePointMeasurementService {

    private final ReferencePointRepository repository;
    private final ReferencePointMapper mapper;
    private final DeviationYearService deviationYearService;
    private final CalculationGeodesicMeasurementService calculationService;
    private final QueryDSLRequestService requestService;

    @Override
    public void save(AcceptableDeviationsGeodesy acceptableDeviationsGeodesy
                   , List<GeodesicMeasurementsPoint> measurements
                   , EquipmentGeodesicMeasurements geodesicMeasurements) {
       if (!measurements.isEmpty()) {
           Integer min = getMin(measurements);
           Long equipmentId = calculationService.getEquipmentId(measurements);
           Set<ReferencePoint> referencePointsDb = requestService.getAllReferencePoint(equipmentId);
           if (!referencePointsDb.isEmpty() && referencePointsDb.size() == measurements.size()) {
               update(acceptableDeviationsGeodesy, min, referencePointsDb, measurements);
               return;
           }
           List<ReferencePoint> referencePoints = repository.saveAll(
                                               measurements.stream()
                                                       .filter(m -> m.getReferencePointValue() != null)
                                                       .map(g -> mapper.mapToReferencePoint(g, geodesicMeasurements))
                                                       .map(r -> getReferencePoint(acceptableDeviationsGeodesy, r, min))
                                                       .toList());
           deviationYearService.save(referencePoints);
       }
    }

    private void update(AcceptableDeviationsGeodesy acceptableDeviationsGeodesy
                      , Integer min
                      , Set<ReferencePoint> referencePoints
                      , List<GeodesicMeasurementsPoint> measurements) {
        Map<Integer, ReferencePoint> points = referencePoints
                                                     .stream()
                                                     .collect(Collectors.toMap(ReferencePoint::getPlaceNumber, p -> p));
        if (points.isEmpty()) {
            deviationYearService.save(repository.saveAll(
                    measurements.stream()
                            .map(m -> mapper.mapToUpdateReferencePoint(points.get(m.getNumberMeasurementLocation()), m))
                            .map(r -> getReferencePoint(acceptableDeviationsGeodesy, r, min))
                            .toList()));

        }
    }

    private ReferencePoint getReferencePoint(AcceptableDeviationsGeodesy acceptableDeviationsGeodesy
                                            , ReferencePoint point
                                            , Integer min) {
        Integer deviation = calculationService.getDeviation(min, point.getCalculatedHeight());
        Integer precipitation = calculationService.getPrecipitation(deviation, point.getDeviationYeas());
        return mapper.mapWithReferencePointData(point
                                             , deviation
                                             , precipitation
                                             , comparePrecipitation(precipitation
                                                           , acceptableDeviationsGeodesy.getAcceptablePrecipitation()));
    }

    private boolean comparePrecipitation(Integer precipitation, Integer acceptablePrecipitation) {
        if (acceptablePrecipitation != null) {
            return precipitation > acceptablePrecipitation;
        }
        return false;
    }

    private int getMin(List<GeodesicMeasurementsPoint> measurements) {
        return calculationService.getMinMeasurement(measurements.stream()
                .map(GeodesicMeasurementsPoint::getReferencePointValue)
                .toList());
    }
}