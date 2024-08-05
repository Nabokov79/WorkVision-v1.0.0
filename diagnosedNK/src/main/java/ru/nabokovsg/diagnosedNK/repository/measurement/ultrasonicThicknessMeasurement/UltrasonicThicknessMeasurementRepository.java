package ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicThicknessMeasurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurement;

public interface UltrasonicThicknessMeasurementRepository extends JpaRepository<UltrasonicThicknessMeasurement, Long> {
}