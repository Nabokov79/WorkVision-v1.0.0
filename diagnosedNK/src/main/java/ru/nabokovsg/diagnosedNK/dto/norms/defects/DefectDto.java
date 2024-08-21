package ru.nabokovsg.diagnosedNK.dto.norms.defects;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.MeasuredParameterDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения информации о дефекте")
public class DefectDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование дефекта")
    private String defectName;
    @Schema(description = "Указание недопустимости дефекта")
    private Boolean notMeetRequirements;
    @Schema(description = "Использовать дефект для расчета остаточной толщины")
    private Boolean useCalculateThickness;
    @Schema(description = "Требуемые вычисления параметров дефекта")
    private String calculation;
    @Schema(description = "Измеряемые параметры дефекта")
    private List<MeasuredParameterDto> measuredParameters;
}