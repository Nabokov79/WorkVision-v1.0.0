package ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.standardSize;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления информации типоразмера элемента или подэлемента")
public class NewStandardSizeDto {

    @Schema(description = "Проектная толщина элемента(подэлемента)")
    private Double designThickness;
    @Schema(description = "Минимальный диаметр(для тройника, перехода)")
    private Integer minDiameter;
    @Schema(description = "Максимальный диаметр(для тройника, перехода)")
    private Integer maxDiameter;
}