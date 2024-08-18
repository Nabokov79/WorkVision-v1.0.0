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

import java.util.*;
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
        GeodesicMeasurementsPoint measurement = measurements.get(measurementDto.getNumberMeasurementLocation());
        if (measurement == null) {
            measurements.put(measurementDto.getNumberMeasurementLocation()
                    , repository.save(mapper.mapToGeodesicMeasurementsPoint(measurementDto, 1)));
        } else {
            measurements.put(measurement.getNumberMeasurementLocation(), update(measurementDto, measurement));
        }
        calculated(measurementDto, new ArrayList<>(measurements.values()));
        return measurements.values()
                .stream()
                .map(mapper::mapToResponseGeodesicMeasurementsPointDto)
                .toList();
    }

    private GeodesicMeasurementsPoint update(GeodesicMeasurementsPointDto measurementDto
                                           , GeodesicMeasurementsPoint measurement) {
        return repository.save(mapper.mapToUpdateGeodesicMeasurementsPoint(measurement, measurementDto
                , calculationService.getMeasurementNumber(measurement.getMeasurementNumber())));
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

    private void calculated(GeodesicMeasurementsPointDto measurementDto, List<GeodesicMeasurementsPoint> measurements) {
        EquipmentDiagnosed equipment = equipmentDiagnosedService.getById(measurementDto.getEquipmentId());
        if (validate(measurements, equipment.getGeodesyLocations())) {
            EquipmentGeodesicMeasurements geodesicMeasurements = equipmentGeodesicMeasurementsService.getByEquipmentId(
                    calculationService.getEquipmentId(measurements));
            measurements = calculationService.recalculateByTransition(measurements);
            AcceptableDeviationsGeodesy acceptableDeviationsGeodesy =
                    acceptableDeviationsGeodesyService.get(equipment, measurementDto.getFull());
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

    private boolean validate(List<GeodesicMeasurementsPoint> measurements, Integer geodesyLocations) {
        Set<Integer> measurementNumbers = new TreeSet<>();
        measurements.forEach(m-> measurementNumbers.add(m.getMeasurementNumber()));
        return measurementNumbers.size() != 1 && measurements.size() == geodesyLocations;
    }
}