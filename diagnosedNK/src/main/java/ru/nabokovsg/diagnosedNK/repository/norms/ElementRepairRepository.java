package ru.nabokovsg.diagnosedNK.repository.norms;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;

public interface ElementRepairRepository extends JpaRepository<ElementRepair, Long> {

    ElementRepair findByRepairName(String repairName);
}