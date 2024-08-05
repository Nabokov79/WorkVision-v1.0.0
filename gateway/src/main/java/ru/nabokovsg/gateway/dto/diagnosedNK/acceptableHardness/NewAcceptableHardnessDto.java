package ru.nabokovsg.gateway.dto.diagnosedNK.acceptableHardness;

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
@Schema(description = "Данные для добавления допустимых толщин элементов оборудования")
public class NewAcceptableHardnessDto {

    @Schema(description = "Идентификатор типа оборудования")
    @NotNull(message = "equipmentTypeId should not be null")
    @Positive(message = "equipmentTypeId can only be positive")
    private Long equipmentTypeId;
    @Schema(description = "Идентификатор элемента оборудования")
    @NotNull(message = "elementId should not be null")
    @Positive(message = "elementId can only be positive")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента элемента оборудования")
    private Long partElementId;
    @Schema(description = "Минимальный измеряемый диаметр элемента")
    @NotNull(message = "minAllowableDiameter should not be null")
    @Positive(message = "minAllowableDiameter can only be positive")
    private Integer minAllowableDiameter;
    @Schema(description = "Минимальная измеряемая толщина элемента")
    @NotNull(message = "minAllowableThickness should not be null")
    @Positive(message = "minAllowableThickness can only be positive")
    private Double minAllowableThickness;
    @Schema(description = "Минимальная допустимая твердость металла элемента")
    @NotNull(message = "minHardness should not be null")
    @Positive(message = "minHardness can only be positive")
    private Integer minHardness;
    @Schema(description = "Максимальная допустимая твердость металла элемента")
    @NotNull(message = "maxHardness should not be null")
    @Positive(message = "maxHardness can only be positive")
    private Integer maxHardness;
    @Schema(description = "Допустимая погрешность измерения")
    @NotNull(message = "measurementError should not be null")
    @Positive(message = "measurementError can only be positive")
    private Float measurementError;
}