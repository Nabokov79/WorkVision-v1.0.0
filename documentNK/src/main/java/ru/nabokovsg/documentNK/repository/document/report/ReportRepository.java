package ru.nabokovsg.documentNK.repository.document.report;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.document.report.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByDiagnosticDocumentId(Long diagnosticDocumentId);
}