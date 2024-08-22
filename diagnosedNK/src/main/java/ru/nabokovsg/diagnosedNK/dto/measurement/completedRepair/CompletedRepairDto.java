package ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Результаты измерения ремонта элемента, подэлемента оборудования")
public class CompletedRepairDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор диагностируемого оборудования")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Идентификатор ремонта")
    private Long repairId;
    @Schema(description = "Количество ремонтов")
    private int quantity;
    @Schema(description = "Измеренные параметры выполненного ремонта элемента")
    private List<ParameterMeasurementDto> parameterMeasurements;
}