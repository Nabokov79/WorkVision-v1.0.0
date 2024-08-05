package ru.nabokovsg.documentNK.dto.template.reportSurvey.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Краткие сведения о шаблоне отчета")
public class ShortResponseReportTemplateDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Название документа")
    private String title;
    @Schema(description = "Заголовок документа")
    private String subtitle;
    @Schema(description = "Строка наименования объекта")
    private String equipmentText;
}