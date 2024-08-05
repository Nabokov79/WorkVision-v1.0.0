package ru.nabokovsg.documentNK.repository.document.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.document.common.CellTable;

public interface CellTableRepository extends JpaRepository<CellTable, Long> {
}