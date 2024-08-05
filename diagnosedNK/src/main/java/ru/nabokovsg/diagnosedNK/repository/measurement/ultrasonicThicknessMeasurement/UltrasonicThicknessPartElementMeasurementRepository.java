package ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicThicknessMeasurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessPartElementMeasurement;

public interface UltrasonicThicknessPartElementMeasurementRepository
        extends JpaRepository<UltrasonicThicknessPartElementMeasurement, Long> {

    UltrasonicThicknessPartElementMeasurement findByPartElementId(Long partElementId);
}