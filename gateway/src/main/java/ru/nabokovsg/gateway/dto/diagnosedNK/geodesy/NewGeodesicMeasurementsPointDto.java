package ru.nabokovsg.gateway.dto.diagnosedNK.geodesy;

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
@Schema(description = "Данные для добавления результатов измерений в одном месте проведения геодезии(нивелирования)")
public class NewGeodesicMeasurementsPointDto {

    @Schema(description = "Идентификатор диагностируемого оборудования")
    @NotNull(message = "equipmentId should not be null")
    @Positive(message = "equipmentId can only be positive")
    private Long equipmentId;
    @Schema(description = "Порядковый номер измерения")
    @NotNull(message = "sequentialNumber should not be null")
    @Positive(message = "sequentialNumber can only be positive")
    private Integer sequentialNumber;
    @Schema(description = "Номер места проведения измерения")
    @NotNull(message = "numberMeasurementLocation should not be null")
    @Positive(message = "numberMeasurementLocation can only be positive")
    private Integer numberMeasurementLocation;
    @Schema(description = "Измеренное значение высоты по реперу")
    private Integer referencePointValue;
    @Schema(description = "Измеренное значение высоты по контрольной точке")
    @NotNull(message = "controlPointValue should not be null")
    @Positive(message = "controlPointValue can only be positive")
    private Integer controlPointValue;
    @Schema(description = "Измеренное значение при смене положения прибора(переход)")
    private Integer transitionValue;
}