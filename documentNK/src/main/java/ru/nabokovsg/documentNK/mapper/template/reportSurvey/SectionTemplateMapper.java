package ru.nabokovsg.documentNK.mapper.template.reportSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.ResponseSectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.SectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.ShortResponseSectionTemplateDto;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;

@Mapper(componentModel = "spring")
public interface SectionTemplateMapper {

    @Mapping(source = "sectionDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "sectionDto.sectionName", target = "sectionName")
    @Mapping(source = "sectionDto.specifyEquipmentPassport", target = "specifyEquipmentPassport")
    @Mapping(source = "section.subsectionTemplates", target = "subsectionTemplates")
    @Mapping(source = "section.protocolReportTemplates", target = "protocolReportTemplates")
    @Mapping(source = "section.reportTemplate", target = "reportTemplate")
    @Mapping(source = "section.id", target = "id")
    SectionTemplate mapToSectionTemplate(SectionTemplateDto sectionDto, SectionTemplate section);

    @Mapping(source = "sectionDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "sectionDto.sectionName", target = "sectionName")
    @Mapping(source = "sectionDto.specifyEquipmentPassport", target = "specifyEquipmentPassport")
    @Mapping(source = "reportTemplate", target = "reportTemplate")
    @Mapping(target = "id", ignore = true)
    SectionTemplate mapToNewSectionTemplate(SectionTemplateDto sectionDto, ReportTemplate reportTemplate);

    ResponseSectionTemplateDto mapToResponseSectionTemplateDto(SectionTemplate section);

    ShortResponseSectionTemplateDto mapToShortResponseSectionTemplateDto(SectionTemplate section);
}