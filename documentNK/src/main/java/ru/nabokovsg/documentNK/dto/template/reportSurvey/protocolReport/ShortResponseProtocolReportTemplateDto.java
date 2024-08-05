package ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Краткие данные шаблона протокола отчета")
public class ShortResponseProtocolReportTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Заголовок с порядковым номером протокола")
    private String title;
    @Schema(description = "Подзаголовок протокола")
    private String subtitle;
}