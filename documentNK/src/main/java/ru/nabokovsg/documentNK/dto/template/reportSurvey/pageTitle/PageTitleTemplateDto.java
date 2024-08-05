package ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения данных титульного листа")
public class PageTitleTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор типа документа")
    private Long documentTypeId;
    @Schema(description = "Идентификатор типа оборудования")
    private Long equipmentTypeId;
    @Schema(description = "Строка наименования оборудования")
    private String equipmentText;
    @Schema(description = "Строка наименования места расположения оборудования")
    private String installationLocation;
    @Schema(description = "Населенный пункт")
    private String city;
}