package ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.documentNK.dto.template.common.conclusion.ResponseConclusionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.ResponseSubsectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.table.ResponseTableTemplateDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные шаблона протокола отчета")
public class ResponseProtocolReportTemplateDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Заголовок с порядковым номером протокола")
    private String title;
    @Schema(description = "Подзаголовок протокола")
    private String subtitle;
    @Schema(description = "Текст пользователя после подзаголовка")
    private String userTextAfterSubtitle;
    @Schema(description = "Шаблоны подразделов")
    private List<ResponseSubsectionTemplateDto> subsections;
    @Schema(description = "Шаблон таблиц")
    private List<ResponseTableTemplateDto> tableTemplates;
    @Schema(description = "Заключение по результатм")
    private ResponseConclusionTemplateDto conclusionTemplate;
}