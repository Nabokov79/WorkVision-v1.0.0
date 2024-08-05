package ru.nabokovsg.documentNK.service.document.report;

import ru.nabokovsg.documentNK.model.document.report.Section;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;

public interface ProtocolReportService {

    void save(Section section, SectionTemplate sectiontemplate, WorkJournalDto journal);
}