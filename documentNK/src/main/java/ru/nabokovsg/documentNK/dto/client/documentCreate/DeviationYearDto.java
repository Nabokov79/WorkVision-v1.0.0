package ru.nabokovsg.documentNK.dto.client.documentCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Значения отклонений по годам")
public class DeviationYearDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Год")
    private Integer year;
    @Schema(description = "Значение отклонения в месте измерения")
    private Integer deviation;
}