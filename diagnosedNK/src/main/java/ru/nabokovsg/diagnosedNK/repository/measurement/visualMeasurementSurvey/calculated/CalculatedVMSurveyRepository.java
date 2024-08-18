package ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.calculated;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.calculated.CalculatedVMSurvey;

import java.util.Set;

public interface CalculatedVMSurveyRepository extends JpaRepository<CalculatedVMSurvey, Long> {

    CalculatedVMSurvey findByEquipmentIdAndElementId(Long equipmentId, Long elementId);

    Set<CalculatedVMSurvey> findAllByEquipmentId(Long equipmentId);
}