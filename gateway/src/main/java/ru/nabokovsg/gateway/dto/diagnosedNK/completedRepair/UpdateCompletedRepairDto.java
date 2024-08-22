package ru.nabokovsg.gateway.dto.diagnosedNK.completedRepair;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.gateway.dto.diagnosedNK.parameterMeasurement.UpdateParameterMeasurementDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для изменения результатов измерения мест ремонта элемента, подэлемента оборудования")
public class UpdateCompletedRepairDto {

    @Schema(description = "Идентификатор диагностируемого оборудования")
    @NotNull(message = "equipmentId should not be null")
    @Positive(message = "equipmentId can only be positive")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    @NotNull(message = "elementId should not be null")
    @Positive(message = "elementId can only be positive")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Идентификатор ремонта")
    @NotNull(message = "repairId should not be null")
    @Positive(message = "repairId can only be positive")
    private Long repairId;
    @Schema(description = "Количество ремонтов")
    private int quantity;
    @Schema(description = "Измеренные параметры выполненного ремонта элемента")
    private List<@Valid UpdateParameterMeasurementDto> parameterMeasurements;
}