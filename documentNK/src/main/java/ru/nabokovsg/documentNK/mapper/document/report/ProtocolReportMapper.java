package ru.nabokovsg.documentNK.mapper.document.report;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.model.document.common.Conclusion;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;
import ru.nabokovsg.documentNK.model.document.report.Section;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;

@Mapper(componentModel = "spring")
public interface ProtocolReportMapper {

    @Mapping(source = "template.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "template.title", target = "title")
    @Mapping(source = "template.subtitle", target = "subtitle")
    @Mapping(source = "template.userTextAfterSubtitle", target = "userTextAfterSubtitle")
    @Mapping(source = "section", target = "section")
    @Mapping(source = "conclusion", target = "conclusion")
    @Mapping(target = "subsection", ignore = true)
    @Mapping(target = "table", ignore = true)
    @Mapping(target = "id", ignore = true)
    ProtocolReport mapToProtocolReport(ProtocolReportTemplate template, Section section, Conclusion conclusion);
}