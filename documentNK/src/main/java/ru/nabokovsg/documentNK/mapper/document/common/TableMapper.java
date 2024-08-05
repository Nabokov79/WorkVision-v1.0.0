package ru.nabokovsg.documentNK.mapper.document.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;
import ru.nabokovsg.documentNK.model.template.common.TableTemplate;

@Mapper(componentModel = "spring")
public interface TableMapper {

    DocumentTable mapToDocumentTable(TableTemplate tableTemplate);

    @Mapping(source = "tableTemplate.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "tableTemplate.tableName", target = "tableName")
    @Mapping(source = "tableTemplate.textBeforeTable", target = "textBeforeTable")
    @Mapping(source = "tableTemplate.textAfterTable", target = "textAfterTable")
    @Mapping(source = "protocol", target = "protocolReport")
    @Mapping(target = "surveyProtocol", ignore = true)
    @Mapping(target = "protocolControl", ignore = true)
    @Mapping(target = "id", ignore = true)
    DocumentTable mapWithProtocolReport(TableTemplate tableTemplate, ProtocolReport protocol);

    @Mapping(source = "tableTemplate.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "tableTemplate.tableName", target = "tableName")
    @Mapping(source = "tableTemplate.textBeforeTable", target = "textBeforeTable")
    @Mapping(source = "tableTemplate.textAfterTable", target = "textAfterTable")
    @Mapping(source = "protocol", target = "protocolControl")
    @Mapping(target = "surveyProtocol", ignore = true)
    @Mapping(target = "protocolReport", ignore = true)
    @Mapping(target = "id", ignore = true)
    DocumentTable mapWithProtocolControl(TableTemplate tableTemplate, ProtocolControl protocol);

    @Mapping(source = "tableTemplate.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "tableTemplate.tableName", target = "tableName")
    @Mapping(source = "tableTemplate.textBeforeTable", target = "textBeforeTable")
    @Mapping(source = "tableTemplate.textAfterTable", target = "textAfterTable")
    @Mapping(source = "protocol", target = "surveyProtocol")
    @Mapping(target = "protocolReport", ignore = true)
    @Mapping(target = "protocolControl", ignore = true)
    @Mapping(target = "id", ignore = true)
    DocumentTable mapWithSurveyProtocol(TableTemplate tableTemplate, SurveyProtocol protocol);
}