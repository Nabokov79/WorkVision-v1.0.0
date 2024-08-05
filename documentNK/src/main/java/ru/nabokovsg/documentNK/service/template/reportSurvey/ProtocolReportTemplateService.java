package ru.nabokovsg.documentNK.service.template.reportSurvey;

import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ProtocolReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ResponseProtocolReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ShortResponseProtocolReportTemplateDto;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;

import java.util.List;

public interface ProtocolReportTemplateService {

    ShortResponseProtocolReportTemplateDto save(ProtocolReportTemplateDto protocolDto);

    ShortResponseProtocolReportTemplateDto update(ProtocolReportTemplateDto protocolDto);

    ResponseProtocolReportTemplateDto get(Long id);

    List<ShortResponseProtocolReportTemplateDto> getAll(Long id);

    void delete(Long id);

    void addSectionTemplate(SectionTemplate template, List<Long> protocolReportTemplatesId);
}