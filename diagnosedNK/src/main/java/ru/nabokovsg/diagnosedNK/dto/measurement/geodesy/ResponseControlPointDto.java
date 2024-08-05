package ru.nabokovsg.diagnosedNK.dto.measurement.geodesy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Рассчитанные результаты измерения по контрольным точкам")
public class ResponseControlPointDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Порядковый номер места измерения")
    private Integer placeNumber;
    @Schema(description = "Рассчитанная высота")
    private Integer calculatedHeight;
    @Schema(description = "Значение отклонения в месте измерения")
    private Integer deviation;
}