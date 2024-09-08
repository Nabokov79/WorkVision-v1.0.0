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
@Schema(description = "Измеряемый параметр")
public class ResponseParameterMeasurementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор параметра")
    private Long parameterId;
    @Schema(description = "Наименование параметра")
    private String parameterName;
    @Schema(description = "Измеренное значение параметра")
    private Double value;
    @Schema(description = "Единица измерения параметра")
    private String unitMeasurement;
}