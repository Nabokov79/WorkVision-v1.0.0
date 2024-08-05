package ru.nabokovsg.documentNK.dto.template.protocolSurvey;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.documentNK.dto.template.common.appendices.AppendicesTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.conclusion.ResponseConclusionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.documentHeader.ResponseDocumentHeaderTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.ResponseSubsectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.table.ResponseTableTemplateDto;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные шаблона протокола по обследованию")
public class ResponseSurveyProtocolTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Заголовок")
    private Set<ResponseDocumentHeaderTemplateDto> leftHeaderTemplate;
    @Schema(description = "Название документа")
    private String title;
    @Schema(description = "Заголовок документа")
    private String subtitle;
    @Schema(description = "Шаблоны подразделов")
    private List<ResponseSubsectionTemplateDto> subsectionTemplates;
    @Schema(description = "Шаблоны таблиц")
    private List<ResponseTableTemplateDto> tableTemplates;
    @Schema(description = "Заключение по результатам")
    private ResponseConclusionTemplateDto conclusionTemplate;
    @Schema(description = "Приложения")
    private List<AppendicesTemplateDto> appendices;
}