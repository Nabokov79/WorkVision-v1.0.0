package ru.nabokovsg.documentNK.repository.document.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;

public interface TableRepository extends JpaRepository<DocumentTable, Long> {
}