package ru.nabokovsg.documentNK.repository.document.report;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.documentNK.model.document.report.PageTitle;

public interface PageTitleRepository extends JpaRepository<PageTitle, Long> {

    PageTitle findByDiagnosticDocumentId(Long id);
}