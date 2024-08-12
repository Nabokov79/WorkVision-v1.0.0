package ru.nabokovsg.diagnosedNK.repository.measurement.geodesy;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.EquipmentGeodesicMeasurements;

public interface EquipmentGeodesicMeasurementsRepository extends JpaRepository<EquipmentGeodesicMeasurements, Long> {

    EquipmentGeodesicMeasurements findByEquipmentId(Long equipmentId);
}