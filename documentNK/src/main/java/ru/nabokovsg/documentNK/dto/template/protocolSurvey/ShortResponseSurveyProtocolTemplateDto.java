package ru.nabokovsg.documentNK.dto.template.protocolSurvey;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.documentNK.dto.template.common.documentHeader.ResponseDocumentHeaderTemplateDto;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Краткие данные протокола по обследованию")
public class ShortResponseSurveyProtocolTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Заголовок")
    private ResponseDocumentHeaderTemplateDto leftHeaderTemplate;
    @Schema(description = "Название документа")
    private String title;
    @Schema(description = "Заголовок документа")
    private String subtitle;
}