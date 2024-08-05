package ru.nabokovsg.documentNK.dto.template.common.conclusion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения шаблона заключений документа")
public class ConclusionTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор")
    private Long documentTypeId;
    @Schema(description = "Основной текст положительного заключения")
    private String positiveText;
    @Schema(description = "Основной текст отрицательного заключения")
    private String negativeText;
}
