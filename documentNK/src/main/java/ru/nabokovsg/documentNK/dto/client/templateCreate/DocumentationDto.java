package ru.nabokovsg.documentNK.dto.client.templateCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные нормативно-технической документации")
public class DocumentationDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Вид документа")
    private String view;
    @Schema(description = "Номер документа")
    private String number;
    @Schema(description = "Заголовок документа")
    private String title;
}