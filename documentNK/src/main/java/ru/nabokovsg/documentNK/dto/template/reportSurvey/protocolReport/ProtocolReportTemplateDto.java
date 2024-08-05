package ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения шаблона протокола отчета")
public class ProtocolReportTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор типа документа")
    private Long documentTypeId;
    @Schema(description = "Порядковый номер протокола")
    private Integer sequentialNumber;
    @Schema(description = "Текст пользователя после заголовка")
    private String userTextAfterSubtitle;
    @Schema(description = "Идентификаторы подразделов")
    private List<Long> subsectionTemplatesId;
    @Schema(description = "Идентификаторы таблицы")
    private List<Long> tableTemplatesId;
}