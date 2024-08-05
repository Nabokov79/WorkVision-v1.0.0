package ru.nabokovsg.gateway.dto.diagnosedNK.ultrasonicControl;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для изменения результата ультразвукового контроля сварного соединения")
public class UpdateUltrasonicMeasurementDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Идентификатор записи в журнале обследований")
    @NotNull(message = "workJournalId should not be null")
    @Positive(message = "workJournalId can only be positive")
    private Long workJournalId;
    @Schema(description = "Типоразмер соединяемых элементов")
    @NotBlank(message = "standardSize should not be blank")
    private String standardSize;
    @Schema(description = "Порядковый номер сварного соединения")
    private Integer weldedJointNumber;
    @Schema(description = "Описание обнаруженного дефекта")
    @NotBlank(message = "measurement should not be blank")
    private String measurement;
    @Schema(description = "Оценка качества сварного соединения")
    @NotBlank(message = "estimation should not be blank")
    private String estimation;
}