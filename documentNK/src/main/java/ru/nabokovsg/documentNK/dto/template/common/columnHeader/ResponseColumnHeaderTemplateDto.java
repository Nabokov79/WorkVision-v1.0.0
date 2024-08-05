package ru.nabokovsg.documentNK.dto.template.common.columnHeader;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные столбца таблицы")
public class ResponseColumnHeaderTemplateDto {

    @Schema(description = "Индентификатор")
    private long id;
    @Schema(description = "Порядковый номер столбца")
    private Integer sequentialNumber;
    @Schema(description = "Заголовок столбца")
    private String heading;
}