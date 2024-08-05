package ru.nabokovsg.documentNK.mapper.template.reportSurvey;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.documentNK.dto.client.templateCreate.DocumentTypeDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ProtocolReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ResponseProtocolReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ShortResponseProtocolReportTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.ConclusionTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;

@Mapper(componentModel = "spring")
public interface ProtocolReportTemplateMapper {

    @Mapping(source = "diagnosticDocumentType.id", target = "documentTypeId")
    @Mapping(source = "protocolDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "diagnosticDocumentType.title", target = "title")
    @Mapping(source = "diagnosticDocumentType.subtitle", target = "subtitle")
    @Mapping(source = "conclusionTemplate", target = "conclusionTemplate")
    @Mapping(source = "protocolDto.userTextAfterSubtitle", target = "userTextAfterSubtitle")
    @Mapping(source = "protocolDto.id", target = "id")
    ProtocolReportTemplate mapToProtocolReportTemplate(ProtocolReportTemplateDto protocolDto
                                                     , DocumentTypeDto diagnosticDocumentType
                                                     , ConclusionTemplate conclusionTemplate);

    ResponseProtocolReportTemplateDto mapToResponseProtocolReportTemplateDto(ProtocolReportTemplate protocol);

    ShortResponseProtocolReportTemplateDto mapToShortProtocolReportTemplateDto(ProtocolReportTemplate protocol);

    @Mapping(source = "sectionTemplate", target = "sectionTemplate")
    @Mapping(target = "sequentialNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    ProtocolReportTemplate mapWithSectionTemplate(@MappingTarget ProtocolReportTemplate protocolReportTemplate
                                                               , SectionTemplate sectionTemplate);
}