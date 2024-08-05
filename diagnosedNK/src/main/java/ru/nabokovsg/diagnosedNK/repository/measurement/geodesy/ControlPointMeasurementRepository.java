package ru.nabokovsg.diagnosedNK.repository.measurement.geodesy;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.geodesy.ControlPoint;

import java.util.Set;

public interface ControlPointMeasurementRepository extends JpaRepository<ControlPoint, Long> {
}