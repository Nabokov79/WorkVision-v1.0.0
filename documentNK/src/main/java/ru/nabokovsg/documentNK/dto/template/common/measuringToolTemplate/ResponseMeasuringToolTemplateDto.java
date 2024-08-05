package ru.nabokovsg.documentNK.dto.template.common.measuringToolTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные шаблона средства измерения")
public class ResponseMeasuringToolTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Данные средства или прибора измерения")
    private String measuringTool;
}