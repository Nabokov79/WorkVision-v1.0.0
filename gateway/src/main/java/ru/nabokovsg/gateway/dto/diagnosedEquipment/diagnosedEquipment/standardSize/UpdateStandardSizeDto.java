package ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.standardSize;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для изменения информации типоразмера элемента или подэлемента")
public class UpdateStandardSizeDto {

    @Schema(description = "Индентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Проектная толщина элемента(подэлемента)")
    private Double designThickness;
    @Schema(description = "Минимальный диаметр(для тройника, перехода)")
    private Integer minDiameter;
    @Schema(description = "Максимальный диаметр(для тройника, перехода)")
    private Integer maxDiameter;
}