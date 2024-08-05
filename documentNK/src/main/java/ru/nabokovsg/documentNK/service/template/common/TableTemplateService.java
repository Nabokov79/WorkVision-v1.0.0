package ru.nabokovsg.documentNK.service.template.common;

import ru.nabokovsg.documentNK.dto.template.common.table.ResponseTableTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.table.TableTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;

import java.util.List;

public interface TableTemplateService {

    ResponseTableTemplateDto save(TableTemplateDto tableDto);

    ResponseTableTemplateDto update(TableTemplateDto tableDto);

    ResponseTableTemplateDto get(Long id);

    TableTemplate getById(Long id);

    void delete(Long id);

    void addProtocolReportTemplate(ProtocolReportTemplate template, List<Long> tableTemplatesId);

    void addProtocolTemplate(SurveyProtocolTemplate template, List<Long> tableTemplatesId);
}