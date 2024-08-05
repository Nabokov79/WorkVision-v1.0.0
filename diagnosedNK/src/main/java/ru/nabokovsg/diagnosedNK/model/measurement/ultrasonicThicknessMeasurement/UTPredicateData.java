package ru.nabokovsg.diagnosedNK.model.measurement.ultrasonicThicknessMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для получения результатов ультразвуковой толщинометрии")
public class UTPredicateData {

    @Schema(description = "Номер измерения(по схеме)")
    private Integer measurementNumber;
    @Schema(description = "Индентификатор оборудования")
    private Long equipmentId;
    @Schema(description = "Индентификатор элемента")
    private Long elementId;
    @Schema(description = "Индентификатор подэлемента")
    private Long partElementId;
}