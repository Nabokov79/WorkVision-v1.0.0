package ru.nabokovsg.documentNK.service.document.common;

import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

import java.util.Set;

public interface TableService {

    DocumentTable saveForSubsection(TableTemplate tableTemplate, DocumentCreationDataDto documentDataDto);

    void saveForProtocolReport(ProtocolReport protocol, Set<TableTemplate> templates, DocumentCreationDataDto documentDataDto);

    void saveForProtocolControl(ProtocolControl protocol, TableTemplate template);

    void saveForSurveyProtocol(SurveyProtocol protocol, Set<TableTemplate> templates, DocumentCreationDataDto documentDataDto);
}