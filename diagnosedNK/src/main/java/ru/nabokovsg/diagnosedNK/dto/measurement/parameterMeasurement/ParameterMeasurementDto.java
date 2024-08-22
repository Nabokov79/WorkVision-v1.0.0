package ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения измеренного значения параметра дефекта")
public class ParameterMeasurementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор параметра")
    private Long parameterId;
    @Schema(description = "Значение параметра")
    private Double value;
}