package ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.parameterMeasurement.ResponseParameterMeasurementDto;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные визуального и измерительноко контроля")
public class ResponseVisualMeasurementControlDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор записи в журнале обследований")
    private Long workJournalId;
    @Schema(description = "Идентификатор дефекта")
    private Long defectId;
    @Schema(description = "Наименование дефекта")
    private String defectName;
    @Schema(description = "Типоразмер соединяемых изделий")
    private String standardSize;
    @Schema(description = "Порядковый номер сварного соединения")
    private Integer weldedJointNumber;
    @Schema(description = "Координаты расположения дефекта")
    private String coordinates;
    @Schema(description = "Заключение о годности сварного соеднинения")
    @NotNull(message = "estimation should not be null")
    private String estimation;
    @Schema(description = "Рассчитанные параметры дефекта")
    private Set<ResponseParameterMeasurementDto> parameterMeasurements;
}