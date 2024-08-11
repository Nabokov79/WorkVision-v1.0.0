package ru.nabokovsg.diagnosedNK.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentDiagnosed;

import java.util.Set;

public interface EquipmentDiagnosedRepository extends JpaRepository<EquipmentDiagnosed, Long> {

    Set<EquipmentDiagnosed> findAllByBuildingId(Long buildingId);
}