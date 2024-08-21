package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedElement;

public interface CalculatedElementRepository extends JpaRepository<CalculatedElement, Long> {
}