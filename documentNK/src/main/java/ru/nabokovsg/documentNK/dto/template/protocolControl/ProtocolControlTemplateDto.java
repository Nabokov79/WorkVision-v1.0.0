package ru.nabokovsg.documentNK.dto.template.protocolControl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения протокола по результатам контроля качества")
public class ProtocolControlTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор типа отчетного документа")
    private Long documentTypeId;
    @Schema(description = "Идентификатор подразделов протокола")
    private List<Long> subsectionTemplatesId;
    @Schema(description = "Идентификатор таблицы")
    private Long tableTemplateId;
}