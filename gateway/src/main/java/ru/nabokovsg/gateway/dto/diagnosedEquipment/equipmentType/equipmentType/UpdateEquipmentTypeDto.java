package ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для изменения информации о виде оборудования")
public class UpdateEquipmentTypeDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Наименование")
    @NotBlank(message = "equipmentName should not be blank")
    private String equipmentName;
    @Schema(description = "Модель")
    private String model;
    @Schema(description = "Ориентация оборудования")
    private String orientation;
}