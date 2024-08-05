package ru.nabokovsg.documentNK.dto.template.common.documentationTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные шаблона нормативно-технической документации")
public class ResponseDocumentationTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Порядковый номер")
    private Integer sequentialNumber;
    @Schema(description = "Нормативно-технический документ")
    private String documentName;
}