package ru.nabokovsg.diagnosedNK.repository.measurement.ultrasonicThicknessMeasurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurement;

import java.util.Set;

public interface UltrasonicThicknessElementMeasurementRepository
       extends JpaRepository<UltrasonicThicknessElementMeasurement, Long> {

    UltrasonicThicknessElementMeasurement findByEquipmentIdAndElementId(Long equipmentId, Long elementId);

    Set<UltrasonicThicknessElementMeasurement> findAllByEquipmentId(Long equipmentId);
}
