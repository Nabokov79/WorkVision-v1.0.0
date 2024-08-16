package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.GeodesicMeasurementsPointDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseGeodesicMeasurementsPointDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy.GeodesicMeasurementsPointMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentDiagnosed;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;
import ru.nabokovsg.diagnosedNK.model.norms.AcceptableDeviationsGeodesy;
import ru.nabokovsg.diagnosedNK.repository.measurement.geodesy.GeodesicMeasurementsPointRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentDiagnosedService;
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
    private final EquipmentDiagnosedService equipmentDiagnosedService;
    private final AcceptableDeviationsGeodesyService acceptableDeviationsGeodesyService;
    private final ReferencePointMeasurementService referencePointMeasurementService;
    private final PointDifferenceService pointDifferenceService;
    private final CalculationGeodesicMeasurementService calculationService;
    private final EquipmentGeodesicMeasurementsService equipmentGeodesicMeasurementsService;

    @Override
    public List<ResponseGeodesicMeasurementsPointDto> save(GeodesicMeasurementsPointDto measurementDto) {
        Map<Integer, GeodesicMeasurementsPoint> measurements = repository.findAllByEquipmentId(
                        measurementDto.getEquipmentId())
                .stream()
                .collect(Collectors.toMap(GeodesicMeasurementsPoint::getNumberMeasurementLocation, g -> g));
        measurements.put(measurementDto.getNumberMeasurementLocation()
                , repository.save(mapper.mapToGeodesicMeasurementsPoint(measurementDto, 1)));
        calculated(measurementDto.getEquipmentId(), measurementDto.getFull(), new ArrayList<>(measurements.values()));
        return measurements.values()
                .stream()
                .map(mapper::mapToResponseGeodesicMeasurementsPointDto)
                .toList();
    }

    @Override
    public List<ResponseGeodesicMeasurementsPointDto> update(GeodesicMeasurementsPointDto measurementDto) {
        Map<Integer, GeodesicMeasurementsPoint> measurements = repository.findAllByEquipmentId(
                        measurementDto.getEquipmentId())
                .stream()
                .collect(Collectors.toMap(GeodesicMeasurementsPoint::getNumberMeasurementLocation, g -> g));
        GeodesicMeasurementsPoint measurement = measurements.get(measurementDto.getNumberMeasurementLocation());
        measurements.put(measurement.getNumberMeasurementLocation()
                , repository.save(mapper.mapToGeodesicMeasurementsPoint(measurementDto
                        , calculationService.getMeasurementNumber(measurement.getMeasurementNumber()))));
        if (size(measurements)) {
            calculated(measurementDto.getEquipmentId()
                     , measurementDto.getFull()
                     , new ArrayList<>(measurements.values()));
        }
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

    private void calculated(Long equipmentId, boolean full, List<GeodesicMeasurementsPoint> measurements) {
        EquipmentDiagnosed equipment = equipmentDiagnosedService.getById(equipmentId);
        if (measurements.size() == equipment.getGeodesyLocations()) {
            EquipmentGeodesicMeasurements geodesicMeasurements = equipmentGeodesicMeasurementsService.getByEquipmentId(
                    calculationService.getEquipmentId(measurements));
            measurements = calculationService.recalculateByTransition(measurements);
            AcceptableDeviationsGeodesy acceptableDeviationsGeodesy =
                    acceptableDeviationsGeodesyService.get(equipment, full);
            referencePointMeasurementService.save(acceptableDeviationsGeodesy
                    , measurements.stream()
                            .filter(m -> m.getReferencePointValue() != null)
                            .toList()
                    , geodesicMeasurements);
            pointDifferenceService.save(acceptableDeviationsGeodesy
                    , measurements.stream()
                            .filter(m -> m.getControlPointValue() != null)
                            .toList()
                    , geodesicMeasurements);
        }
    }

    private boolean size(Map<Integer, GeodesicMeasurementsPoint> measurements) {
        return measurements.values()
                .stream()
                .map(GeodesicMeasurementsPoint::getMeasurementNumber)
                .distinct()
                .toList()
                .size() == 1;
    }
}