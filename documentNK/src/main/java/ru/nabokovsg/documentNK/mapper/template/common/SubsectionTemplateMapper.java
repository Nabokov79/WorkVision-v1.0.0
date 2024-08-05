package ru.nabokovsg.documentNK.mapper.template.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.documentNK.dto.client.templateCreate.BranchDto;
import ru.nabokovsg.documentNK.dto.client.templateCreate.DepartmentDto;
import ru.nabokovsg.documentNK.dto.client.templateCreate.DivisionDto;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.ResponseSubsectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.SubsectionTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.SectionTemplate;

@Mapper(componentModel = "spring")
public interface SubsectionTemplateMapper {

    SubsectionTemplate mapToSubsectionTemplate(SubsectionTemplateDto subsectionDto);

    @Mapping(source = "subsectionDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "subsectionDto.subsectionName", target = "subsectionName")
    @Mapping(source = "subsectionDto.userText", target = "userText")
    @Mapping(source = "subsectionDto.summaryResults", target = "summaryResults")
    @Mapping(target = "id", ignore = true)
    SubsectionTemplate mapToUpdateSubsectionTemplate(@MappingTarget SubsectionTemplate subsectionsTemplate
                                                                  , SubsectionTemplateDto subsectionDto);

    @Mapping(source = "tableTemplate", target = "tableTemplate")
    @Mapping(target = "sequentialNumber", ignore = true)
    @Mapping(target = "protocolReportTemplate", ignore = true)
    @Mapping(target = "id", ignore = true)
    SubsectionTemplate mapWithTableTemplate(@MappingTarget SubsectionTemplate subsectionsTemplate
                                                         , TableTemplate tableTemplate);

    @Mapping(source = "protocolReportTemplate", target = "protocolReportTemplate")
    @Mapping(target = "sequentialNumber", ignore = true)
    @Mapping(target = "sectionTemplate", ignore = true)
    @Mapping(target = "id", ignore = true)
    SubsectionTemplate mapWithProtocolReportTemplate(@MappingTarget SubsectionTemplate subsectionsTemplate
                                                                  , ProtocolReportTemplate protocolReportTemplate);

    @Mapping(source = "division", target = "division")
    SubsectionTemplate mapWithDivisionContact(@MappingTarget SubsectionTemplate subsectionsTemplate
                                                           , String division);

    @Mapping(source = "sectionTemplate", target = "sectionTemplate")
    @Mapping(target = "sequentialNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    SubsectionTemplate mapWithSectionTemplate(@MappingTarget SubsectionTemplate subsectionsTemplate
                                                           , SectionTemplate sectionTemplate);

    @Mapping(source = "protocolTemplate", target = "surveyProtocolTemplate")
    @Mapping(target = "sequentialNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    SubsectionTemplate mapWithProtocolTemplate(@MappingTarget SubsectionTemplate subsectionsTemplate
                                                            , SurveyProtocolTemplate protocolTemplate);

    @Mapping(source = "protocolControlTemplate", target = "protocolControlTemplate")
    @Mapping(target = "sequentialNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    SubsectionTemplate mapWithProtocolControlTemplate(@MappingTarget SubsectionTemplate subsectionsTemplate
                                                                   , ProtocolControlTemplate protocolControlTemplate);

    ResponseSubsectionTemplateDto mapToResponseSubsectionTemplateDto(SubsectionTemplate subsection);

    DivisionDto mapFromBranch(BranchDto branchDto);

    DivisionDto mapFromDepartment(DepartmentDto departmentDto);
}