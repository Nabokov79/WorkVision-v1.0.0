package ru.nabokovsg.documentNK.service.document.report;

import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.model.document.report.Report;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;

public interface SectionService {

    void save(Report report, ReportTemplate template, DocumentCreationDataDto documentCreationDataDto);
}