package ru.nabokovsg.diagnosedNK.dto.norms.elementRepair;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter.MeasuredParameterDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные добавления/изменения способа ремонта")
public class ElementRepairDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование типа ремонта")
    private String repairName;
    @Schema(description = "Требуемые вычисления параметров ремонта элемента")
    private String calculation;
    @Schema(description = "Измеряемые параметры ремонта элемента")
    private List<MeasuredParameterDto> measuredParameters;
}