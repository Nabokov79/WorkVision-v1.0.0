package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.ExaminedPartElement;

public interface ExaminedPartElementRepository extends JpaRepository<ExaminedPartElement, Long> {

    ExaminedPartElement findByPartElementId(Long partElementId);
}