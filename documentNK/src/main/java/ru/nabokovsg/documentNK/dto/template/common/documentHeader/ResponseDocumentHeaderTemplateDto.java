package ru.nabokovsg.documentNK.dto.template.common.documentHeader;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные заголовка документа")
public class ResponseDocumentHeaderTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование структурного подразделения")
    private String division;
    @Schema(description = "Адрес")
    private String address;
    @Schema(description = "Сведения об аттестации структурного подразделения")
    private String certificate;
    @Schema(description = "Контакты структурного подразделения")
    private String contact;
}