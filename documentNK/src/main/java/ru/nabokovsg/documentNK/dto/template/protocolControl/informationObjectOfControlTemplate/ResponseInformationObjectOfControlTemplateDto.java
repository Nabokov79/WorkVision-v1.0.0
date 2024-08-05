package ru.nabokovsg.documentNK.dto.template.protocolControl.informationObjectOfControlTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные шаблона общих данных контролируемого объекта")
public class ResponseInformationObjectOfControlTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Порядковый номер")
    private Integer sequentialNumber;
    @Schema(description = "Наименование типа данных контролируемого объекта")
    private String dataName;
}