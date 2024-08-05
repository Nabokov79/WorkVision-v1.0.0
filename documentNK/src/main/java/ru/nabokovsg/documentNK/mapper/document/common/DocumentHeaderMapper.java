package ru.nabokovsg.documentNK.mapper.document.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.documentNK.model.document.common.DocumentHeader;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.PageTitle;
import ru.nabokovsg.documentNK.model.template.common.DocumentHeaderTemplate;

@Mapper(componentModel = "spring")
public interface DocumentHeaderMapper {

    DocumentHeader mapToDocumentHeader(DocumentHeaderTemplate documentHeaders);

    @Mapping(source = "pageTitle", target = "pageTitle")
    DocumentHeader mapWithPageTitle(@MappingTarget DocumentHeader documentHeaders, PageTitle pageTitle);

    @Mapping(source = "protocol", target = "surveyProtocol")
    DocumentHeader mapWithSurveyProtocol(@MappingTarget DocumentHeader documentHeaders, SurveyProtocol protocol);

    @Mapping(source = "protocol", target = "protocolControl")
    DocumentHeader mapWithProtocolControl(@MappingTarget DocumentHeader documentHeaders, ProtocolControl protocol);
}
