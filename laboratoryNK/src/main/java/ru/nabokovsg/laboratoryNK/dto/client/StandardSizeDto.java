package ru.nabokovsg.laboratoryNK.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные типоразмера элемента или подэлемента")
public class StandardSizeDto {

    @Schema(description = "Идентификатор")
    private long id;
    @Schema(description = "Проектная толщина элемента(подэлемента)")
    private Double designThickness;
    @Schema(description = "Наружный диаметр(для элемента трубопровода)")
    private Integer outerDiameter;
    @Schema(description = "Минимальный диаметр(для тройника, перехода)")
    private Integer minDiameter;
    @Schema(description = "Максимальный диаметр(для тройника, перехода)")
    private Integer maxDiameter;
}