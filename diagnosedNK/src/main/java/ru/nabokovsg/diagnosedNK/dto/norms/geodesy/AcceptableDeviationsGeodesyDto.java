package ru.nabokovsg.diagnosedNK.dto.norms.geodesy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения значений норм " +
        "допустимых отклонений значений геодезических измерений")
public class AcceptableDeviationsGeodesyDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор типа оборудования")
    private Long equipmentTypeId;
    @Schema(description = "Полное или пустое оборудование")
    private Boolean full;
    @Schema(description = "Старое или новое оборудование")
    private Boolean old;
    @Schema(description = "Объем оборудования")
    private Integer volume;
    @Schema(description = "Максимальная допустимая осадка")
    private Integer acceptablePrecipitation;
    @Schema(description = "Максимальная допустимая разность для соседних точек)")
    private Integer maxDifferenceNeighboringPoints;
    @Schema(description = "Максимальная допустимая разность для диаметральных точек")
    private Integer maxDifferenceDiametricPoints;
}