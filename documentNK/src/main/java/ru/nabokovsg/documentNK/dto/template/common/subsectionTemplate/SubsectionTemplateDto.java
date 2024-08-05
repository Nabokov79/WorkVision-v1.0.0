package ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения шаблона подразделя")
public class SubsectionTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Порядковый номер подраздела")
    private Integer sequentialNumber;
    @Schema(description = "Наименование подраздела")
    private String subsectionName;
    @Schema(description = "Текст пользователя")
    private String userText;
    @Schema(description = "Данных структурного подразделения")
    private DivisionDataDto divisionParam;
    @Schema(description = "Идентификатор таблицы")
    private Long tableId;
    @Schema(description = "Добавить сводную таблицу результатов измерений")
    private boolean summaryResultTable;
}