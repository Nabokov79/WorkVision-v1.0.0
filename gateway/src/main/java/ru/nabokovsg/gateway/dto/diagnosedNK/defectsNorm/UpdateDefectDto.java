package ru.nabokovsg.gateway.dto.diagnosedNK.defectsNorm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.gateway.dto.diagnosedNK.measuredParameter.UpdateMeasuredParameterDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для изменения информации о дефекте")
public class UpdateDefectDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Указание недопустимости дефекта")
    @NotNull(message = "notMeetRequirements should not be null")
    private Boolean notMeetRequirements;
    @Schema(description = "Требуемые вычисления параметров дефекта")
    @NotBlank(message = "calculation should not be blank")
    private String calculation;
    @Schema(description = "Измеряемые параметры дефекта")
    private List<UpdateMeasuredParameterDto> measuredParameters;
}