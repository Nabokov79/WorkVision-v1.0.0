package ru.nabokovsg.gateway.dto.diagnosedNK.visualMeasurementControl;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.gateway.dto.diagnosedNK.parameterMeasurement.NewParameterMeasurementDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.parameterMeasurement.UpdateParameterMeasurementDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Добавить результаты измерения дефекта сварного соединения")
public class UpdateVisualMeasurementControlDto {

    @Schema(description = "Идентификатор записи в журнале обследований")
    @NotNull(message = "workJournalId should not be null")
    @Positive(message = "workJournalId can only be positive")
    private Long workJournalId;
    @Schema(description = "Порядковый номер сварного соединения")
    @NotNull(message = "weldedJointNumber should not be null")
    @Positive(message = "weldedJointNumber can only be positive")
    private Integer weldedJointNumber;
    @Schema(description = "Типоразмер соединяемых элементов")
    @NotBlank(message = "standardSize should not be blank")
    private String standardSize;
    @Schema(description = "Идентификатор дефекта")
    @NotNull(message = "defectId should not be null")
    @Positive(message = "defectId can only be positive")
    private Long defectId;
    @Schema(description = "Координаты расположения дефекта")
    private String coordinates;
    @Schema(description = "Заключение о годности сварного соеднинения")
    @NotNull(message = "estimation should not be null")
    private boolean estimation;
    @Schema(description = "Измеренные параметры выполненного ремонта элемента")
    private List<@Valid UpdateParameterMeasurementDto> parameterMeasurements;
}