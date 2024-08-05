package ru.nabokovsg.laboratoryNK.dto.remark;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные замечания к документу или чертежу")
public class ResponseRemarkDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Вид документа диагностики")
    private String documentType;
    @Schema(description = "Номер документа")
    private Integer documentNumber;
    @Schema(description = "Замечание к документу или чертежу")
    private String remark;
    @Schema(description = "Фамилия, инициалы сотрудника")
    private String initials;
    @Schema(description = "Отметка об исправлении замечания")
    private Boolean documentCorrected;
}
