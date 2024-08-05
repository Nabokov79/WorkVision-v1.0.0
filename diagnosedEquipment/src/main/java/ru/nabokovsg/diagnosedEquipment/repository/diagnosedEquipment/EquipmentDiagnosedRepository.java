package ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentDiagnosed;

import java.util.Set;

public interface EquipmentDiagnosedRepository extends JpaRepository<EquipmentDiagnosed, Long> {

    Set<EquipmentDiagnosed> findAllByBuildingId(Long buildingId);
}