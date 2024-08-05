package ru.nabokovsg.documentNK.dto.template.protocolSurvey;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения шаблона протокола по обследованию")
public class SurveyProtocolTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор типа оборудования")
    private Long equipmentTypeId;
    @Schema(description = "Идентификатор типа отчетного документа")
    private Long documentTypeId;
    @Schema(description = "Идентификатор подразделов протокола")
    private List<Long> subsectionTemplatesId;
    @Schema(description = "Идентификатор таблиц протокола")
    private List<Long> tableTemplatesId;
}