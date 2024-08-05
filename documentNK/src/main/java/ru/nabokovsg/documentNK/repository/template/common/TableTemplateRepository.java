package ru.nabokovsg.documentNK.repository.template.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

public interface TableTemplateRepository extends JpaRepository<TableTemplate, Long> {
}