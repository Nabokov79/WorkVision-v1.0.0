package ru.nabokovsg.diagnosedNK.repository.measurement.hardnessMeasurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.hardnessMeasurement.HardnessMeasurement;

public interface HardnessMeasurementRepository extends JpaRepository<HardnessMeasurement, Long> {
}