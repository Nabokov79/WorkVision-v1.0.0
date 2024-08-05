package ru.nabokovsg.documentNK.dto.client.documentCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Рассчитанные результаты измерения по реперам")
public class ReferencePointDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Порядковый номер места измерения")
    private Integer placeNumber;
    @Schema(description = "Рассчитанная высота")
    private Integer calculatedHeight;
    @Schema(description = "Значение отклонения в месте измерения")
    private Integer deviation;
    @Schema(description = "Значение осадки в месте измерения")
    private Integer precipitation;
    @Schema(description = "Значения отклонений по годам")
    private List<DeviationYearDto> deviationYeas;
    @Schema(description = "Допустимость значения осадки")
    private Boolean acceptablePrecipitation;
}