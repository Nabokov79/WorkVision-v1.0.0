package ru.nabokovsg.documentNK.mapper.document.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.documentNK.model.document.common.DocumentTable;
import ru.nabokovsg.documentNK.model.document.common.Subsection;
import ru.nabokovsg.documentNK.model.document.protocol.ProtocolControl;
import ru.nabokovsg.documentNK.model.document.protocol.SurveyProtocol;
import ru.nabokovsg.documentNK.model.document.report.ProtocolReport;
import ru.nabokovsg.documentNK.model.document.report.Section;
import ru.nabokovsg.documentNK.model.template.common.SubsectionTemplate;

@Mapper(componentModel = "spring")
public interface SubsectionMapper {

    @Mapping(source = "template.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "template.subsectionName", target = "subsectionName")
    @Mapping(source = "template.userText", target = "userText")
    @Mapping(source = "template.division", target = "division")
    @Mapping(target = "table", ignore = true)
    @Mapping(target = "documentation", ignore = true)
    @Mapping(target = "measuringTools", ignore = true)
    @Mapping(target = "protocolReport", ignore = true)
    @Mapping(source = "section", target = "section")
    @Mapping(target = "surveyProtocol", ignore = true)
    @Mapping(target = "protocolControl", ignore = true)
    @Mapping(target = "id", ignore = true)
    Subsection mapWithSection(SubsectionTemplate template, Section section);

    @Mapping(source = "table", target = "table")
    @Mapping(source = "subsection.id", target = "id")
    Subsection mapWithDocumentTable(@MappingTarget Subsection subsection, DocumentTable table);

    @Mapping(source = "template.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "template.subsectionName", target = "subsectionName")
    @Mapping(source = "template.userText", target = "userText")
    @Mapping(source = "template.division", target = "division")
    @Mapping(source = "protocol", target = "protocolReport")
    @Mapping(target = "table", ignore = true)
    @Mapping(target = "documentation", ignore = true)
    @Mapping(target = "measuringTools", ignore = true)
    @Mapping(target = "protocolReport", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "surveyProtocol", ignore = true)
    @Mapping(target = "protocolControl", ignore = true)
    @Mapping(target = "id", ignore = true)
    Subsection mapWithProtocolReport(SubsectionTemplate template, ProtocolReport protocol);

    @Mapping(source = "template.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "template.subsectionName", target = "subsectionName")
    @Mapping(source = "template.userText", target = "userText")
    @Mapping(source = "template.division", target = "division")
    @Mapping(source = "protocol", target = "protocolControl")
    @Mapping(target = "table", ignore = true)
    @Mapping(target = "documentation", ignore = true)
    @Mapping(target = "measuringTools", ignore = true)
    @Mapping(target = "protocolReport", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "surveyProtocol", ignore = true)
    @Mapping(target = "protocolReport", ignore = true)
    @Mapping(target = "id", ignore = true)
    Subsection mapWithProtocolControl(SubsectionTemplate template, ProtocolControl protocol);

    @Mapping(source = "template.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "template.subsectionName", target = "subsectionName")
    @Mapping(source = "template.userText", target = "userText")
    @Mapping(source = "template.division", target = "division")
    @Mapping(source = "protocol", target = "surveyProtocol")
    @Mapping(target = "table", ignore = true)
    @Mapping(target = "documentation", ignore = true)
    @Mapping(target = "measuringTools", ignore = true)
    @Mapping(target = "protocolReport", ignore = true)
    @Mapping(target = "section", ignore = true)
    @Mapping(target = "protocolControl", ignore = true)
    @Mapping(target = "protocolReport", ignore = true)
    @Mapping(target = "id", ignore = true)
    Subsection mapWithSurveyProtocol(SubsectionTemplate template, SurveyProtocol protocol);
}