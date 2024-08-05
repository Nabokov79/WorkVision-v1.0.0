package ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentPassport;

import java.util.Set;

public interface EquipmentPassportRepository extends JpaRepository<EquipmentPassport, Long> {

    Set<EquipmentPassport> findAllByEquipmentDiagnosedId(Long id);
}