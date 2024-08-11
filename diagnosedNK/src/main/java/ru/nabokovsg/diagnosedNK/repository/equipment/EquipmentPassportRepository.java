package ru.nabokovsg.diagnosedNK.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPassport;

import java.util.Set;

public interface EquipmentPassportRepository extends JpaRepository<EquipmentPassport, Long> {

    Set<EquipmentPassport> findAllByEquipmentDiagnosedId(Long id);
}