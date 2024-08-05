package ru.nabokovsg.documentNK.repository.template.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.common.MeasuringToolTemplate;

public interface MeasuringToolTemplateRepository extends JpaRepository<MeasuringToolTemplate, Long> {

    boolean existsByMeasuringToolId(Long measuringToolId);
}