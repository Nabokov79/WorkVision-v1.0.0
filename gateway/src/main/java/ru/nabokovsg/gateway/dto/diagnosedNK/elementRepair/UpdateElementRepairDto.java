package ru.nabokovsg.gateway.dto.diagnosedNK.elementRepair;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@Schema(description = "Данные изменения способа ремонта")
public class UpdateElementRepairDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Наименование типа ремонта")
    @NotBlank(message = "repairName should not be blank")
    private String repairName;
    @Schema(description = "Требуемые вычисления параметров ремонта элемента")
    @NotBlank(message = "calculation should not be blank")
    private String calculation;
    @Schema(description = "Измеряемые параметры ремонта элемента")
    private List<@Valid UpdateMeasuredParameterDto> measuredParameters;
}