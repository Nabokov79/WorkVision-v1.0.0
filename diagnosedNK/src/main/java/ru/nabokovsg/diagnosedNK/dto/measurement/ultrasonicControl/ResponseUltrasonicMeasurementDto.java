package ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicControl;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные  результата ультразвукового контроля сварного соединения")
public class ResponseUltrasonicMeasurementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор записи рабочего журнала")
    private Long workJournalId;
    @Schema(description = "Типоразмер соединяемых элементов")
    private String standardSize;
    @Schema(description = "Порядковый номер сварного соединения")
    private Integer weldedJointNumber;
    @Schema(description = "Описание обнаруженного дефекта")
    private String measurement;
    @Schema(description = "Оценка качества сварного соединения")
    private String estimation;
}