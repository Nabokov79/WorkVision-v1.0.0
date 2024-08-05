package ru.nabokovsg.laboratoryNK.dto.remark;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения замечания к документу или чертежу")
public class RemarkDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор документа")
    private Long documentId;
    @Schema(description = "Замечание к документу или чертежу")
    private String remark;
    @Schema(description = "Идентификатор сотрудника")
    private Long employeeId;
    @Schema(description = "Отметка об исправлении замечания")
    private Boolean documentCorrected;
}