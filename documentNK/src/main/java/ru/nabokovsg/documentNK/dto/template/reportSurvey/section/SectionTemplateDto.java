package ru.nabokovsg.documentNK.dto.template.reportSurvey.section;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения заголовка раздела документа")
public class SectionTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор шаблона отчета")
    private Long reportTemplateId;
    @Schema(description = "Порядковый номер")
    private Integer sequentialNumber;
    @Schema(description = "Название")
    private String sectionName;
    @Schema(description = "Указать в разделе данные паспорта оборудования")
    private Boolean specifyEquipmentPassport;
    @Schema(description = "Идентификаторы подразделов")
    private List<Long> subsectionTemplatesId;
    @Schema(description = "Идентификаторы протоколов отчета")
    private List<Long> protocolReportTemplatesId;
}