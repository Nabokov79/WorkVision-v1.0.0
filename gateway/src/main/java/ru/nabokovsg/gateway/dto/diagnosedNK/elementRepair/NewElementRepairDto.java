package ru.nabokovsg.gateway.dto.diagnosedNK.elementRepair;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.gateway.dto.diagnosedNK.measuredParameter.NewMeasuredParameterDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные добавления/изменения способа ремонта")
public class NewElementRepairDto {

    @Schema(description = "Наименование типа ремонта")
    @NotBlank(message = "repairName should not be blank")
    private String repairName;
    @Schema(description = "Требуемые вычисления параметров ремонта элемента")
    @NotBlank(message = "calculation should not be blank")
    private String calculation;
    @Schema(description = "Измеряемые параметры ремонта элемента")
    private List<@Valid NewMeasuredParameterDto> measuredParameters;
}