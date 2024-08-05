package ru.nabokovsg.gateway.dto.diagnosedNK.defectsNorm;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.gateway.dto.diagnosedNK.measuredParameter.NewMeasuredParameterDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления информации о дефекте")
public class NewDefectDto {

    @Schema(description = "Наименование дефекта")
    @NotBlank(message = "defectName should not be blank")
    private String defectName;
    @Schema(description = "Указание недопустимости дефекта")
    @NotNull(message = "notMeetRequirements should not be null")
    private Boolean notMeetRequirements;
    @Schema(description = "Требуемые вычисления параметров дефекта")
    @NotBlank(message = "type document should not be blank")
    private String calculation;
    @Schema(description = "Измеряемые параметры дефекта")
    private List<@Valid NewMeasuredParameterDto> measuredParameters;
}