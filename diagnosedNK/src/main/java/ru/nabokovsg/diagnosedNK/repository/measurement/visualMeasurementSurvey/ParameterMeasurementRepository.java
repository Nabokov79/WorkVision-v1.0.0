package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ParameterMeasurement;

public interface ParameterMeasurementRepository extends JpaRepository<ParameterMeasurement, Long> {
}