package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CalculatedPartElement;

public interface CalculatedPartElementRepository extends JpaRepository<CalculatedPartElement, Long> {
}