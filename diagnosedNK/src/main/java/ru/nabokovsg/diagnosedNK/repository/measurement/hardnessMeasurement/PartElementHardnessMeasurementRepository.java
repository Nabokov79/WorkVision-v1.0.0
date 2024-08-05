package ru.nabokovsg.diagnosedNK.repository.measurement.hardnessMeasurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.PartElementHardnessMeasurement;

public interface PartElementHardnessMeasurementRepository extends JpaRepository<PartElementHardnessMeasurement, Long> {

    PartElementHardnessMeasurement findByPartElementId(Long partElementId);
}