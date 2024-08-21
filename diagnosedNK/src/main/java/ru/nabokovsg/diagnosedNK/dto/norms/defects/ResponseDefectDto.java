package ru.nabokovsg.diagnosedNK.dto.norms.defects;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.ResponseMeasuredParameterDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные дефекта")
public class ResponseDefectDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование дефекта")
    private String defectName;
    @Schema(description = "Указание недопустимости дефекта")
    private Boolean notMeetRequirements;
    @Schema(description = "Использовать дефект для расчета остаточной толщины")
    private Boolean useCalculateThickness;
    @Schema(description = "Измеряемые параметры дефекта")
    private List<ResponseMeasuredParameterDto> measuredParameters;
}