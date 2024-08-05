package ru.nabokovsg.gateway.dto.diagnosedNK.qualityControlDefect;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.gateway.dto.diagnosedNK.parameterMeasurement.NewParameterMeasurementDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Результаты измерения дефекта сварного соединения")
public class NewQualityControlDefectDto {

    @Schema(description = "Идентификатор записи в журнале обследований")
    @NotNull(message = "workJournalId should not be null")
    @Positive(message = "workJournalId can only be positive")
    private Long workJournalId;
    @Schema(description = "Порядковый номер сварного соединения")
    @NotNull(message = "weldedJointNumber should not be null")
    @Positive(message = "weldedJointNumber can only be positive")
    private Integer weldedJointNumber;
    @Schema(description = "Идентификатор дефекта")
    private Long defectId;
    @Schema(description = "Измеренные параметры выполненного ремонта элемента")
    private List<@Valid NewParameterMeasurementDto> parameterMeasurements;
}