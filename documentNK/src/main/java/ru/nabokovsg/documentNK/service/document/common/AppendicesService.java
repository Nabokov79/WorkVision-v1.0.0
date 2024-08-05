package ru.nabokovsg.documentNK.service.document.common;

import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.Report;
import ru.nabokovsg.documentNK.model.template.common.AppendicesTemplate;

import java.util.Set;

public interface AppendicesService {

    void saveForReport(Report report, Set<AppendicesTemplate> templates);

    void saveForSurveyProtocol(SurveyProtocol protocol, Set<AppendicesTemplate> templates);
}