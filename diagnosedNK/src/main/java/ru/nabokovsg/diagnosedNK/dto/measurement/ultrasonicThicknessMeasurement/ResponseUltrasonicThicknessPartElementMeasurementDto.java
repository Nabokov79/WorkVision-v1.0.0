package ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Результаты ультразвукового измерения толщины подэлемента")
public class ResponseUltrasonicThicknessPartElementMeasurementDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Наименование подэлемента")
    private String partElementName;
    @Schema(description = "Результаты ультразвукового измерения толщины")
    private ResponseUltrasonicThicknessMeasurementDto measurement;
}