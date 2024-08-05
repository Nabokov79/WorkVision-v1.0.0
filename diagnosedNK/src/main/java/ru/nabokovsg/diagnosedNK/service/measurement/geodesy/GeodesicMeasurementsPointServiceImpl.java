package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.GeodesicMeasurementsPointDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseGeodesicMeasurementsPointDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy.GeodesicMeasurementsPointMapper;
import ru.nabokovsg.diagnosedNK.model.diagnosticEquipmentData.DiagnosticEquipmentData;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableDeviationsGeodesy;
import ru.nabokovsg.diagnosedNK.repository.measurement.geodesy.GeodesicMeasurementsPointRepository;
import ru.nabokovsg.diagnosedNK.service.diagnosticEquipmentData.DiagnosticEquipmentDataService;
import ru.nabokovsg.diagnosedNK.service.norms.AcceptableDeviationsGeodesyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeodesicMeasurementsPointServiceImpl implements GeodesicMeasurementsPointService {

    private final GeodesicMeasurementsPointRepository repository;
    private final GeodesicMeasurementsPointMapper mapper;
    private final DiagnosticEquipmentDataService diagnosticObjectDataService;
    private final AcceptableDeviationsGeodesyService acceptableDeviationsGeodesyService;
    private final ReferencePointMeasurementService referencePointMeasurementService;
    private final PointDifferenceService pointDifferenceService;
    private final CalculationGeodesicMeasurementService calculationService;

    @Override
    public List<ResponseGeodesicMeasurementsPointDto> save(GeodesicMeasurementsPointDto measurementDto) {
        Map<Integer, GeodesicMeasurementsPoint> measurements = repository.findAllByEquipmentId(
                                                                                        measurementDto.getEquipmentId())
                                .stream()
                                .collect(Collectors.toMap(GeodesicMeasurementsPoint::getNumberMeasurementLocation, g -> g));
        measurements.put(measurementDto.getNumberMeasurementLocation()
                , repository.save(mapper.mapToGeodesicMeasurementsPoint(measurementDto)));
        calculated(measurementDto.getEquipmentId(), new ArrayList<>(measurements.values()));
        return measurements.values()
                           .stream()
                           .map(mapper::mapToResponseGeodesicMeasurementsPointDto)
                           .toList();
    }

    @Override
    public List<ResponseGeodesicMeasurementsPointDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                .stream()
                .map(mapper::mapToResponseGeodesicMeasurementsPointDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("GeodesicMeasurement with id=%s not found for delete", id));
    }

    private void calculated(Long equipmentId, List<GeodesicMeasurementsPoint> measurements) {
        DiagnosticEquipmentData object = diagnosticObjectDataService.get(equipmentId);
        if (measurements.size() == object.getGeodesyLocations()) {
            measurements = calculationService.recalculateByTransition(measurements);
            AcceptableDeviationsGeodesy acceptableDeviationsGeodesy = acceptableDeviationsGeodesyService.get(object);
            referencePointMeasurementService.save(acceptableDeviationsGeodesy
                                                , measurements.stream()
                                                              .filter(m -> m.getReferencePointValue() != null)
                                                              .toList());
            pointDifferenceService.save(acceptableDeviationsGeodesy
                                      , measurements.stream()
                                                    .filter(m -> m.getControlPointValue() != null)
                                                    .toList());
        }
    }
}