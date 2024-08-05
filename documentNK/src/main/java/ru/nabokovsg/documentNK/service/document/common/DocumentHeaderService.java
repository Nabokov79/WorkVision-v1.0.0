package ru.nabokovsg.documentNK.service.document.common;

import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.PageTitle;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;

import java.util.Set;

public interface DocumentHeaderService {

    void saveForPageTitle(PageTitle pageTitle, Set<DocumentHeaderTemplate> documentHeaders);

    void saveForSurveyProtocol(SurveyProtocol protocol, Set<DocumentHeaderTemplate> documentHeaders);

    void saveForProtocolControl(ProtocolControl protocol, Set<DocumentHeaderTemplate> documentHeaders);
}