package ru.nabokovsg.gateway.dto.laboratoryNK.documentRemark;

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
@Schema(description = "Данные для изменения замечания к документу или чертежу")
public class UpdateRemarkDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "remark id should not be null")
    @Positive(message = "remark id can only be positive")
    private Long id;
    @Schema(description = "Идентификатор документа")
    @NotNull(message = "document id should not be null")
    @Positive(message = "document id can only be positive")
    private Long documentId;
    @Schema(description = "Замечание к документу или чертежу")
    @NotBlank(message = "remark should not be blank")
    private String remark;
    @Schema(description = "Идентификатор сотрудника")
    @NotNull(message = "employee id should not be null")
    @Positive(message = "employee id can only be positive")
    private Long employeeId;
    @Schema(description = "Отметка об исправлении замечания")
    private Boolean documentCorrected;
}