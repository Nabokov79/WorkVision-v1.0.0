package ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.parameterMeasurement;

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

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Порядковый номер измеренного дефекта или ремонта элемента(подэлемента)")
    private Integer measurementNumber;
    @Schema(description = "Порядковый номер измеренного параметра дефекта")
    private Integer parameterNumber;
    @Schema(description = "Наименование параметра")
    private String parameterName;
    @Schema(description = "Единица измерения параметра")
    private String unitMeasurement;
    @Schema(description = "Рассчитанное минимальное значение параметра")
    private Double minValue;
    @Schema(description = "Рассчитанное максимальное значение параметра")
    private Double maxValue;
}