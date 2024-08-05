package ru.nabokovsg.documentNK.mapper.template.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.documentNK.dto.template.common.table.ResponseTableTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.table.TableTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;
import ru.nabokovsg.documentNK.model.template.protocolControl.ProtocolControlTemplate;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ProtocolReportTemplate;

@Mapper(componentModel = "spring")
public interface TableTemplateMapper {

    @Mapping(source = "tableDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "tableType", target = "tableType")
    @Mapping(source = "tableDto.tableName", target = "tableName")
    @Mapping(source = "tableDto.textBeforeTable", target = "textBeforeTable")
    @Mapping(source = "tableDto.textAfterTable", target = "textAfterTable")
    @Mapping(source = "tableDto.id", target = "id")
    TableTemplate mapToTableTemplate(TableTemplateDto tableDto, String tableType);

    ResponseTableTemplateDto mapToResponseTableTemplateDto(TableTemplate table);

    @Mapping(source = "protocolReportTemplate", target = "protocolReportTemplate")
    @Mapping(target = "sequentialNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    TableTemplate mapWithProtocolReportTemplate(@MappingTarget TableTemplate tableTemplate
                                                             , ProtocolReportTemplate protocolReportTemplate);

    @Mapping(source = "protocolTemplate", target = "surveyProtocolTemplate")
    @Mapping(target = "sequentialNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    TableTemplate mapWithProtocolTemplate(@MappingTarget TableTemplate tableTemplate
                                                       , SurveyProtocolTemplate protocolTemplate);

    @Mapping(source = "protocolControlTemplate", target = "protocolControlTemplate")
    @Mapping(target = "sequentialNumber", ignore = true)
    @Mapping(target = "id", ignore = true)
    TableTemplate mapWithProtocolTemplate(@MappingTarget TableTemplate tableTemplate
                                                       , ProtocolControlTemplate protocolControlTemplate);
}