package ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения информации о виде оборудования")
public class EquipmentTypeDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование")
    private String equipmentName;
    @Schema(description = "Модель")
    private String model;
    @Schema(description = "Ориентация оборудования")
    private String orientation;
}