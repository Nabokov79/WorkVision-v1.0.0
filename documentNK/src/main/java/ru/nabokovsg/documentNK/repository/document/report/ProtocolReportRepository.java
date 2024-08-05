package ru.nabokovsg.documentNK.repository.document.report;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;

public interface ProtocolReportRepository extends JpaRepository<ProtocolReport, Long> {
}