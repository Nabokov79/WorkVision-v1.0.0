package ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.identifiedDefect;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.parameterMeasurement.ParameterMeasurementDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Результаты измерения дефекта элемента, подэлемента оборудования")
public class IdentifiedDefectDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор диагностируемого оборудования")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Идентификатор дефекта")
    private Long defectId;
    @Schema(description = "Измеренные параметры дефекта")
    private List<ParameterMeasurementDto> parameterMeasurements;
}