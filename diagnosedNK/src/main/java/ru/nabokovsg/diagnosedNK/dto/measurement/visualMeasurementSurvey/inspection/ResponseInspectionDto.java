package ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Результат визуального осмотра элемента(подэлемента")
public class ResponseInspectionDto {

    @Schema(description = "Индентификатор элемента")
    private Long elementId;
    @Schema(description = "Индентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Результат визуального осмотра элемента(подэлемента")
    private String inspection;
}
