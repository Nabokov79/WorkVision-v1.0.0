package ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Результаты ультразвукового измерения толщины элемента")
public class ResponseUltrasonicThicknessElementMeasurementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор оборудования")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Результаты ультразвукового измерения толщины")
    private ResponseUltrasonicThicknessMeasurementDto measurement;
    @Schema(description = "Результаты ультразвукового измерения толщины подэлемента")
    private Set<ResponseUltrasonicThicknessPartElementMeasurementDto> partElementMeasurements;
}