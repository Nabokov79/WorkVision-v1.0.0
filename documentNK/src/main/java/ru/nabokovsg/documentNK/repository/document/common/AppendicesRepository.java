package ru.nabokovsg.documentNK.repository.document.common;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.document.common.Appendices;

public interface AppendicesRepository extends JpaRepository<Appendices, Long> {
}