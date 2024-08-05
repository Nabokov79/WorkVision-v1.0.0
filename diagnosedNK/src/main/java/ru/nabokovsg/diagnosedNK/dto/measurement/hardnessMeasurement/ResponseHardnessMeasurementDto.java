package ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Результаты измерения твердости металла")
public class ResponseHardnessMeasurementDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Диаметер или номинальная толщина элемента(подэлемента)")
    private Integer nominalSize;
    @Schema(description = "Номер измерения(по схеме)")
    private Integer measurementNumber;
    @Schema(description = "Минимальное измереноое значение")
    private Double minMeasurementValue;
    @Schema(description = "Максимальное измеренное значение")
    private Double maxMeasurementValue;
    @Schema(description = "Допустимость измеренного значения")
    private String validity;
    @Schema(description = "Допустимое значение")
    private Boolean acceptable;
    @Schema(description = "Ниже предельного допустимого значения")
    private Boolean invalid;
    @Schema(description = "Приближается к предельному недопустимому значению")
    private Boolean approachingInvalid;
    @Schema(description = "Достигнуто предельное допустимое значение")
    private Boolean reachedInvalid;
    @Schema(description = "Отсутствуют нормы определения брака")
    private Boolean noStandard;
}