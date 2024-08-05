package ru.nabokovsg.documentNK.dto.template.reportSurvey.section;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.ResponseSubsectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.protocolReport.ResponseProtocolReportTemplateDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Полные данные раздела документа")
public class ResponseSectionTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Порядковый номер")
    private Integer sequentialNumber;
    @Schema(description = "Название")
    private String sectionName;
    @Schema(description = "Шаблоны подразделов")
    private List<ResponseSubsectionTemplateDto> subsectionTemplates;
    @Schema(description = "Шаблоны протоколов отчета")
    private List<ResponseProtocolReportTemplateDto> protocolReportTemplates;
}