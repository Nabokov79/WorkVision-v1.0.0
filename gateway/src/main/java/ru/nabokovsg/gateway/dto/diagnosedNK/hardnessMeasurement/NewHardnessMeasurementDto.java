package ru.nabokovsg.gateway.dto.diagnosedNK.hardnessMeasurement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения результата выполнения" +
                      " измерений твердости металла элементов оборудования")
public class NewHardnessMeasurementDto {

    @Schema(description = "Идентификатор оборудования")
    @NotNull(message = "equipmentId should not be null")
    @Positive(message = "equipmentId can only be positive")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    @NotNull(message = "elementId should not be null")
    @Positive(message = "elementId can only be positive")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Номер измерения(по схеме)")
    @NotNull(message = "measurementNumber should not be null")
    @Positive(message = "measurementNumber can only be positive")
    private Integer measurementNumber;
    @Schema(description = "Значение твердости металла")
    @NotNull(message = "measurementValue should not be null")
    @Positive(message = "measurementValue can only be positive")
    private Integer measurementValue;
}