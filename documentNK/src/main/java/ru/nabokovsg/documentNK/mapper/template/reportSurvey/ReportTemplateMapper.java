package ru.nabokovsg.documentNK.mapper.template.reportSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.report.ResponseReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.report.ShortResponseReportTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.AppendicesTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.PageTitleTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ReportTemplateMapper {

    @Mapping(source = "documentTypeId", target = "documentTypeId")
    @Mapping(source = "equipmentTypeId", target = "equipmentTypeId")
    @Mapping(source = "pageTitleTemplate", target = "pageTitleTemplate")
    @Mapping(source = "appendices", target = "appendices")
    @Mapping(target = "id", ignore = true)
    ReportTemplate mapToReportTemplate(Long documentTypeId
                                     , Long equipmentTypeId
                                     , PageTitleTemplate pageTitleTemplate
                                     , Set<AppendicesTemplate> appendices);

    ResponseReportTemplateDto mapToResponseReportTemplateDto(ReportTemplate reportTemplate);

    ShortResponseReportTemplateDto mapToShortResponseReportTemplateDto(PageTitleTemplate pageTitleTemplate);
}