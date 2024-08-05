package ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControl;

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
@Schema(description = "Данные для добавления сведений об объекте контроля")
public class NewInformationAboutObjectOfControlDto {

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