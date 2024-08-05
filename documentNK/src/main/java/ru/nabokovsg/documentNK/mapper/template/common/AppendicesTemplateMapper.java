package ru.nabokovsg.documentNK.mapper.template.common;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.documentNK.dto.template.common.appendices.AppendicesTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.appendices.ResponseAppendicesTemplateDto;
import ru.nabokovsg.documentNK.model.template.common.AppendicesTemplate;
import ru.nabokovsg.documentNK.model.template.protocolSurvey.SurveyProtocolTemplate;
import ru.nabokovsg.documentNK.model.template.reportSurvey.ReportTemplate;

@Mapper(componentModel = "spring")
public interface AppendicesTemplateMapper {

    AppendicesTemplate mapToAppendicesTemplate(AppendicesTemplateDto appendicesDto, String nameOfList);

    ResponseAppendicesTemplateDto mapToResponseAppendicesDto(AppendicesTemplate appendices);

    @Mapping(source = "reportTemplate", target = "reportTemplate")
    @Mapping(target = "id", ignore = true)
    AppendicesTemplate mapWithReportTemplate(@MappingTarget AppendicesTemplate appendices
                                                          , ReportTemplate reportTemplate);

    @Mapping(source = "protocolTemplate", target = "surveyProtocolTemplate")
    AppendicesTemplate mapWithProtocolTemplate(@MappingTarget AppendicesTemplate appendices
                                                            , SurveyProtocolTemplate protocolTemplate);
}