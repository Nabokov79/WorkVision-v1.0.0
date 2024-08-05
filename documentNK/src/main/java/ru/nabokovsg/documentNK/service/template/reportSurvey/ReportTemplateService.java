package ru.nabokovsg.documentNK.service.template.reportSurvey;

import ru.nabokovsg.documentNK.dto.template.reportSurvey.report.ResponseReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.report.ShortResponseReportTemplateDto;
import ru.nabokovsg.documentNK.model.template.reportSurvey.PageTitleTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;

import java.util.List;

public interface ReportTemplateService {

    void create(PageTitleTemplate pageTitleTemplate);

    ResponseReportTemplateDto get(Long id);

    List<ShortResponseReportTemplateDto> getAll();

    ReportTemplate getById(Long id);

    ReportTemplate getByDocumentTypeIdAndEquipmentTypeId(Long documentTypeId, Long equipmentTypeId);
}