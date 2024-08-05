package ru.nabokovsg.gateway.dto.diagnosedNK.parameterMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Данные для добавления измеренного значения параметра дефекта")
public class NewParameterMeasurementDto {

    @Schema(description = "Идентификатор параметра")
    @NotNull(message = "parameterId should not be null")
    @Positive(message = "parameterId can only be positive")
    private Long parameterId;
    @Schema(description = "Значение параметра")
    @NotNull(message = "value should not be null")
    @Positive(message = "value can only be positive")
    private Double value;
}