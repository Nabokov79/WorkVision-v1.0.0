package ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения результат измерения дефекта сварного соединения")
public class VisualMeasurementControlDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор записи в журнале обследований")
    private Long workJournalId;
    @Schema(description = "Порядковый номер сварного соединения")
    private Integer weldedJointNumber;
    @Schema(description = "Идентификатор дефекта")
    private Long defectId;
    @Schema(description = "Типоразмер соединяемых изделий")
    private String standardSize;
    @Schema(description = "Координаты расположения дефекта")
    private String coordinates;
    @Schema(description = "Заключение о годности сварного соеднинения")
    private boolean estimation;
    @Schema(description = "Измеренные параметры дефекта")
    private List<ParameterMeasurementDto> parameterMeasurements;
}