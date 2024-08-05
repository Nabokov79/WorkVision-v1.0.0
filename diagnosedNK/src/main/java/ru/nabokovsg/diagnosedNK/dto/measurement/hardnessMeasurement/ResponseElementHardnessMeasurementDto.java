package ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Рассчитанные результаты измерений твердости металла элемента оборудования")
public class ResponseElementHardnessMeasurementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор оборудования")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Результаты измерения твердости металла")
    private ResponseHardnessMeasurementDto measurement;
    @Schema(description = "Результаты измерения твердости металла подэлемента")
    private Set<ResponsePartElementHardnessMeasurementDto> partElementMeasurements;
}