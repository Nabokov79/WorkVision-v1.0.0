package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseEquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy.EquipmentGeodesicMeasurementsMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.PointDifference;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ReferencePoint;
import ru.nabokovsg.diagnosedNK.repository.measurement.geodesy.EquipmentGeodesicMeasurementsRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EquipmentGeodesicMeasurementsServiceImpl implements EquipmentGeodesicMeasurementsService {

    private final EquipmentGeodesicMeasurementsRepository repository;
    private final EquipmentGeodesicMeasurementsMapper mapper;

    @Override
    public ResponseEquipmentGeodesicMeasurementsDto get(Long equipmentId) {
        return mapper.mapToResponseEquipmentGeodesicMeasurementsDto(
                repository.findByEquipmentId(equipmentId)
                        .orElseThrow(() -> new NotFoundException(
                                String.format("EquipmentGeodesicMeasurements for equipment with id=%s not found"
                                                                                                    , equipmentId))));
    }

    @Override
    public void addReferencePoint(Long equipmentId, List<ReferencePoint> referencePoints) {
        EquipmentGeodesicMeasurements measurement = getByEquipment(equipmentId);
        measurement.setReferencePoints(new HashSet<>(referencePoints));
        save(measurement);
    }

    @Override
    public void addControlPointAndPointDifference(Long equipmentId
                                                , Set<ControlPoint> controlPoints
                                                , Set<PointDifference> pointDifferences) {
        EquipmentGeodesicMeasurements measurement = getByEquipment(equipmentId);
        measurement.setControlPoints(controlPoints);
        measurement.setPointDifferences(pointDifferences);
        save(measurement);
    }

    private void save(EquipmentGeodesicMeasurements measurement) {
        repository.save(measurement);
    }

    private EquipmentGeodesicMeasurements getByEquipment(Long equipmentId) {
        return repository.findByEquipmentId(equipmentId).orElse(repository.save(mapper.mapToEquipmentGeodesicMeasurements(equipmentId)));
    }
}