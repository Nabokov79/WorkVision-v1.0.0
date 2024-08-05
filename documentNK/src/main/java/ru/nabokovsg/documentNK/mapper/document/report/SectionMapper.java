package ru.nabokovsg.documentNK.mapper.document.report;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.model.document.report.Report;
import ru.nabokovsg.documentNK.model.document.report.Section;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mapping(source = "template.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "template.sectionName", target = "sectionName")
    @Mapping(source = "report", target = "report")
    @Mapping(target = "equipmentPassports", ignore = true)
    @Mapping(target = "subsection", ignore = true)
    @Mapping(target = "protocolReport", ignore = true)
    @Mapping(target = "id", ignore = true)
    Section mapToSection(SectionTemplate template, Report report);
}