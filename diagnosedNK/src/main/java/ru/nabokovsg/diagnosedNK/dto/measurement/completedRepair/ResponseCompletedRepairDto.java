package ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ResponseParameterMeasurementDto;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Выполненный ремонт элемента, подэлемента оборудования")
public class ResponseCompletedRepairDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Наименование подэлемента")
    private String partName;
    @Schema(description = "Наименование ремонта")
    private String repairName;
    @Schema(description = "Количество ремонтов")
    private Integer quantity;
    @Schema(description = "Рассчитанные параметры выполненного ремонта элемента")
    private Set<ResponseParameterMeasurementDto> parameterMeasurements;
}