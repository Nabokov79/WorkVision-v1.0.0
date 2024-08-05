package ru.nabokovsg.documentNK.dto.template.common.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.documentNK.dto.template.common.columnHeader.ResponseColumnHeaderTemplateDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные шаблона таблицы")
public class ResponseTableTemplateDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Порядковый номер таблицы")
    private Integer sequentialNumber;
    @Schema(description = "Название таблицы")
    private String tableName;
    @Schema(description = "Текст перед таблицей")
    private String textBeforeTable;
    @Schema(description = "Текст после таблицы")
    private String textAfterTable;
    @Schema(description = "Шаблоны колонок таблицы")
    private List<ResponseColumnHeaderTemplateDto> columnHeaders;
}