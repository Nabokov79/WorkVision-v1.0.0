package ru.nabokovsg.laboratoryNK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.laboratoryNK.model.MeasuringTool;

public interface MeasuringToolRepository extends JpaRepository<MeasuringTool, Long> {
}