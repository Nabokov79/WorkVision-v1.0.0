package ru.nabokovsg.diagnosedNK.service.measurement.geodesy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseEquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.geodesy.EquipmentGeodesicMeasurementsMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.PointDifference;
import ru.nabokovsg.diagnosedNK.repository.measurement.geodesy.EquipmentGeodesicMeasurementsRepository;

import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EquipmentGeodesicMeasurementsServiceImpl implements EquipmentGeodesicMeasurementsService {

    private final EquipmentGeodesicMeasurementsRepository repository;
    private final EquipmentGeodesicMeasurementsMapper mapper;

    @Override
    public ResponseEquipmentGeodesicMeasurementsDto get(Long equipmentId) {
        EquipmentGeodesicMeasurements geodesicMeasurements = repository.findByEquipmentId(equipmentId);
        if (geodesicMeasurements == null) {
            throw new NotFoundException(
                    String.format("EquipmentGeodesicMeasurements for equipment with id=%s not found", equipmentId));
        }
        return mapper.mapToResponseEquipmentGeodesicMeasurementsDto(geodesicMeasurements);
    }

    @Override
    public EquipmentGeodesicMeasurements getByEquipmentId(Long equipmentId) {
        return Objects.requireNonNullElseGet(repository.findByEquipmentId(equipmentId)
                        , () -> repository.save(mapper.mapToEquipmentGeodesicMeasurements(equipmentId))
        );
    }

    @Override
    public void addControlPointAndPointDifference(Long equipmentId
                                                , Set<ControlPoint> controlPoints
                                                , Set<PointDifference> pointDifferences) {
        EquipmentGeodesicMeasurements measurement = getByEquipmentId(equipmentId);
        measurement.setControlPoints(controlPoints);
        measurement.setPointDifferences(pointDifferences);
        save(measurement);
    }

    private void save(EquipmentGeodesicMeasurements measurement) {
        repository.save(measurement);
    }


}