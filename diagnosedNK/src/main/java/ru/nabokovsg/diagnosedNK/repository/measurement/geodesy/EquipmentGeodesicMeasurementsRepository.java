package ru.nabokovsg.diagnosedNK.repository.measurement.geodesy;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;

import java.util.Optional;

public interface EquipmentGeodesicMeasurementsRepository extends JpaRepository<EquipmentGeodesicMeasurements, Long> {

    Optional<EquipmentGeodesicMeasurements> findByEquipmentId(Long equipmentId);
}