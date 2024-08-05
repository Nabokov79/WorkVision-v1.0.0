package ru.nabokovsg.documentNK.dto.document.informationAboutObjectOfControl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения сведений об объекте контроля")
public class InformationAboutObjectOfControlDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор шаблона")
    private Long templateId;
    @Schema(description = "Идентификатор записи рабочего журнала")
    private Long workJournalId;
    @Schema(description = "Сведения об объекте контроля")
    private String dataValue;
}