package ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControlTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления шаблона общих данных контролируемого объекта")
public class NewInformationObjectOfControlTemplateDto {

    @Schema(description = "Идентификатор типа документа диагностики")
    @NotNull(message = "documentTypeId should not be null")
    @Positive(message = "documentTypeId can only be positive")
    private Long documentTypeId;
    @Schema(description = "Порядковый номер")
    @NotNull(message = "sequentialNumber should not be null")
    @Positive(message = "sequentialNumber can only be positive")
    private Integer sequentialNumber;
    @Schema(description = "Наименование типа данных контролируемого объекта")
    @NotBlank(message = "dataName should not be blank")
    private String dataName;
}