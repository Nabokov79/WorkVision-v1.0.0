package ru.nabokovsg.documentNK.dto.template.protocolControl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.documentNK.dto.template.common.documentHeader.ResponseDocumentHeaderTemplateDto;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "ДКраткие днные протокола/заключения по контролю качества")
public class ShortResponseProtocolControlTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Заголовок")
    private ResponseDocumentHeaderTemplateDto leftHeaderTemplate;
    @Schema(description = "Название документа")
    private String title;
    @Schema(description = "Заголовок документа")
    private String subtitle;
}