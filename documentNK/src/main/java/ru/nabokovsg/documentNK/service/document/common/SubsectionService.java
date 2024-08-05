package ru.nabokovsg.documentNK.service.document.common;

import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;
import ru.nabokovsg.documentNK.model.document.report.Section;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;

import java.util.Set;

public interface SubsectionService {

    void saveForSection(Section section, Set<SubsectionTemplate> templates, DocumentCreationDataDto documentCreationDataDto);

    void saveForProtocolReport(ProtocolReport protocol, Set<SubsectionTemplate> templates, DocumentCreationDataDto documentCreationDataDto);

    void saveForProtocolControl(ProtocolControl protocol, Set<SubsectionTemplate> templates, DocumentCreationDataDto documentCreationDataDto);

    void saveForSurveyProtocol(SurveyProtocol protocol, Set<SubsectionTemplate> templates, DocumentCreationDataDto documentCreationDataDto);
}