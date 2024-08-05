package ru.nabokovsg.laboratoryNK.dto.diagnosticDocument;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения вида документа диагностики")
public class DiagnosticDocumentTypeDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Название документа")
    private String title;
    @Schema(description = "Заголовок документа")
    private String subtitle;
    @Schema(description = "Тип документа")
    private String typeDocument;
}