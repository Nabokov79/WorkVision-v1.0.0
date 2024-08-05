package ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControl;

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
@Schema(description = "Данные для изменения сведений об объекте контроля")
public class UpdateInformationAboutObjectOfControlDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Идентификатор шаблона")
    @NotNull(message = "templateId should not be null")
    @Positive(message = "templateId can only be positive")
    private Long templateId;
    @Schema(description = "Идентификатор записи рабочего журнала")
    @NotNull(message = "workJournalId should not be null")
    @Positive(message = "workJournalId can only be positive")
    private Long workJournalId;
    @Schema(description = "сведения об объекте контроля")
    @NotBlank(message = "dataValue should not be blank")
    private String dataValue;
}