package ru.nabokovsg.diagnosedNK.repository.measurement.hardnessMeasurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.ElementHardnessMeasurement;

import java.util.Set;

public interface ElementHardnessMeasurementRepository extends JpaRepository<ElementHardnessMeasurement, Long> {

    ElementHardnessMeasurement  findByEquipmentIdAndElementId(Long equipmentId, Long elementId);

    Set<ElementHardnessMeasurement> findAllByEquipmentId(Long equipmentId);
}