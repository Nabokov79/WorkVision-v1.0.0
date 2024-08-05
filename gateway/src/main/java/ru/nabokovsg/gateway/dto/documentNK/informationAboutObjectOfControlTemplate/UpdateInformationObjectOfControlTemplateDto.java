package ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControlTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для изменения шаблона общих данных контролируемого объекта")
public class UpdateInformationObjectOfControlTemplateDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Идентификатор типа документа диагностики")
    @NotNull(message = "documentTypeId should not be null")
    @Positive(message = "documentTypeId can only be positive")
    private Long documentTypeId;
    @NotNull(message = "sequentialNumber should not be null")
    @Positive(message = "sequentialNumber can only be positive")
    private Integer sequentialNumber;
    @Schema(description = "Наименование типа данных контролируемого объекта")
    @NotBlank(message = "dataName should not be blank")
    private String dataName;
}