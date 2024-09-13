package ru.nabokovsg.diagnosedNK.dto.measurement.geodesy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.model.measurement.enums.GeodesicPointType;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Рассчитанные результаты измерения по контрольным точкам")
public class ResponsePointDifferenceDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Тип расчитанных точек измерения")
    private GeodesicPointType geodesicPointType;
    @Schema(description = "Первое место измерения")
    private Integer firstPlaceNumber;
    @Schema(description = "Второе место измерения")
    private Integer secondPlaceNumber;
    @Schema(description = "Рассчитанная разность между измеренными местами")
    private Integer difference;
    @Schema(description = "Допустимость значения разности")
    private Boolean acceptableDifference;
}