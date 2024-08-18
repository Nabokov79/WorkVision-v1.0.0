package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedDefect;

public interface CalculatedDefectRepository extends JpaRepository<CalculatedDefect, Long> {
}