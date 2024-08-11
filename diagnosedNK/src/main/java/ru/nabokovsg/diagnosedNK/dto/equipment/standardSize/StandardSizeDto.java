package ru.nabokovsg.diagnosedNK.dto.equipment.standardSize;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения информации типоразмера элемента или подэлемента")
public class StandardSizeDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Проектная толщина элемента(подэлемента)")
    private Double designThickness;
    @Schema(description = "Минимальный диаметр(для тройника, перехода)")
    private Integer minDiameter;
    @Schema(description = "Максимальный диаметр(для тройника, перехода)")
    private Integer maxDiameter;
}