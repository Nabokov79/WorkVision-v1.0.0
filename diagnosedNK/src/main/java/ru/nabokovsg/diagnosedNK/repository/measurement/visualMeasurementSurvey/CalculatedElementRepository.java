package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CalculatedElement;

public interface CalculatedElementRepository extends JpaRepository<CalculatedElement, Long> {

    CalculatedElement findByPartElementId(Long partElementId);
}