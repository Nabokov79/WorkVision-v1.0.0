package ru.nabokovsg.documentNK.dto.template.common.columnHeader;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения колонки столбца")
public class ColumnHeaderTemplateDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Порядковый номер столбца")
    private Integer sequentialNumber;
    @Schema(description = "Заголовок столбца")
    private String heading;
    @Schema(description = "Тип заголовка столбца колонки")
    private String columnHeaderType;
}