package ru.nabokovsg.diagnosedNK.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.diagnosedNK.model.equipment.StandardSize;

public interface StandardSizeRepository extends JpaRepository<StandardSize, Long> {
}