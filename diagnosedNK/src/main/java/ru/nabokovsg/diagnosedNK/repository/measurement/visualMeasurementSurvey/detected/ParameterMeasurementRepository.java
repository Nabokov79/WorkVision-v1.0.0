package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;

public interface ParameterMeasurementRepository extends JpaRepository<ParameterMeasurement, Long> {
}