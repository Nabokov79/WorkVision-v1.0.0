package ru.nabokovsg.gateway.dto.diagnosedNK.identifiedDefect;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.gateway.dto.diagnosedNK.parameterMeasurement.UpdateParameterMeasurementDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Результаты измерения дефекта элемента, подэлемента оборудования")
public class UpdateIdentifiedDefectDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
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
    @Schema(description = "Идентификатор дефекта")
    private Long defectId;
    @Schema(description = "Измеренные параметры выполненного ремонта элемента")
    private List<@Valid UpdateParameterMeasurementDto> parameterMeasurements;
}
