package ru.nabokovsg.documentNK.dto.template.common.documentationTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения средства измерения")
public class DocumentationTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор шаблона подраздела документа")
    private Long subsectionId;
    @Schema(description = "Порядковый номер")
    private Integer sequentialNumber;
    @Schema(description = "Идентификатор нормативно-технического документа")
    private Long documentationId;
}