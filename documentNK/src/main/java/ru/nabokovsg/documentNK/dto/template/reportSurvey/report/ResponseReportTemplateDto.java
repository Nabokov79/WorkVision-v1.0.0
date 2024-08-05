package ru.nabokovsg.documentNK.dto.template.reportSurvey.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.documentNK.dto.template.common.appendices.AppendicesTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle.ResponsePageTitleTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.ResponseSectionTemplateDto;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Шаблон отчета")
public class ResponseReportTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Титульный лист")
    private ResponsePageTitleTemplateDto pageTitleTemplate;
    @Schema(description = "Подразделы")
    private Set<ResponseSectionTemplateDto> sectionsTemplate;
    @Schema(description = "Приложения")
    private Set<AppendicesTemplateDto> appendices;
}