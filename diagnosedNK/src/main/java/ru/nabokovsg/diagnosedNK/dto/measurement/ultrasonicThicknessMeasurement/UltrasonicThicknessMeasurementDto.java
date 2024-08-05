package ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Добавление/изменение результата ультразвукового измерения толщины стенки элемента" +
        ", подэлемента оборудования")
public class UltrasonicThicknessMeasurementDto {

    @Schema(description = "Идентификатор оборудования")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Диаметр элемента(для трубопровода)")
    private Integer diameter;
    @Schema(description = "Номер измерения по схеме")
    private Integer measurementNumber;
    @Schema(description = "Минимальное измеренное значение")
    private Double minMeasurementValue;
    @Schema(description = "Максимальное измеренное значение ")
    private Double maxMeasurementValue;
}