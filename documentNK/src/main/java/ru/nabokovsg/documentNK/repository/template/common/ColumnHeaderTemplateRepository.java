package ru.nabokovsg.documentNK.repository.template.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.common.ColumnHeaderTemplate;

public interface ColumnHeaderTemplateRepository extends JpaRepository<ColumnHeaderTemplate, Long> {
}