package ru.nabokovsg.documentNK.service.template.common;

import ru.nabokovsg.documentNK.dto.template.common.appendices.AppendicesTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.appendices.ResponseAppendicesTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.AppendicesTemplate;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;

import java.util.Set;

public interface AppendicesTemplateService {

    ResponseAppendicesTemplateDto save(AppendicesTemplateDto appendicesDto);

    ResponseAppendicesTemplateDto update(AppendicesTemplateDto appendicesDto);

    void delete(Long id);

    Set<AppendicesTemplate> getAllByEquipmentTypeId(Long equipmentTypeId);

    Set<AppendicesTemplate> addReportTemplate(ReportTemplate reportTemplate, Set<AppendicesTemplate> templates);

    void addProtocolTemplate(SurveyProtocolTemplate protocolTemplate);
}