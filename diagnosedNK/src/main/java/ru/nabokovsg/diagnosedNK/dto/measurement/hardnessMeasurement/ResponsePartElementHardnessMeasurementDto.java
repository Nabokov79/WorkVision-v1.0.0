package ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Рассчитанные результаты измерений твердости металла элементов оборудования")
public class ResponsePartElementHardnessMeasurementDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Наименование подэлемента")
    private String partElementName;
    @Schema(description = "Результаты измерения твердости металла")
    private ResponseHardnessMeasurementDto measurement;
}