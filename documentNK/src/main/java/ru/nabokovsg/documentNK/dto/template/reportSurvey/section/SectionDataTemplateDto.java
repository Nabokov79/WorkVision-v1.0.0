package ru.nabokovsg.documentNK.dto.template.reportSurvey.section;

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
@Schema(description = "Данные для добавления разделов документа")
public class SectionDataTemplateDto {

    @Schema(description = "Тип документа")
    private Long headerDocumentId;
    @Schema(description = "Индентификатор типа оборудования")
    private Long equipmentTypeId;
    @Schema(description = "Данные разделов шаблона документа")
    private List<SectionTemplateDto> sections;
}