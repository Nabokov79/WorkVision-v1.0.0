package ru.nabokovsg.laboratoryNK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.laboratoryNK.model.WorkJournal;

import java.time.LocalDate;

public interface WorkJournalRepository extends JpaRepository<WorkJournal, Long> {

    boolean existsByDateAndEquipmentId(LocalDate date, Long equipmentId);
}