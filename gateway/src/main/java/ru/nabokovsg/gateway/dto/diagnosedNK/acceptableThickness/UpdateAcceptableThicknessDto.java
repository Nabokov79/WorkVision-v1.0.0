package ru.nabokovsg.gateway.dto.diagnosedNK.acceptableThickness;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для изменения допустимых толщин элементов оборудования")
public class UpdateAcceptableThicknessDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
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
    @Schema(description = "Диаметр элемента(для трубопроводов)")
    private Integer diameter;
    @Schema(description = "Минимальная допустимая толщина стенки элемента")
    @NotNull(message = "minThickness should not be null")
    @Positive(message = "minThickness can only be positive")
    private Double minThickness;
    @Schema(description = "Минимальная допустимая толщина стенки элемента в процентах")
    private Integer acceptablePercent;
    @Schema(description = "Допустимая погрешность измерения")
    @NotNull(message = "measurementError should not be null")
    @Positive(message = "measurementError can only be positive")
    private Float measurementError;
}