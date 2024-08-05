package ru.nabokovsg.diagnosedNK.repository.measurement.geodesy;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.GeodesicMeasurementsPoint;

import java.util.Set;

public interface GeodesicMeasurementsPointRepository extends JpaRepository<GeodesicMeasurementsPoint, Long> {


    Set<GeodesicMeasurementsPoint> findAllByEquipmentId(Long equipmentId);
}