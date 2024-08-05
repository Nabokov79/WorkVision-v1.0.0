package ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentTypeElement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Данные для добавления элемента оборудования")
public class NewEquipmentTypeElementDto {

    @Schema(description = "Индентификатор оборудования")
    @NotNull(message = "equipmentType id should not be null")
    @Positive(message = "equipmentType id can only be positive")
    private Long equipmentTypeId;
    @Schema(description = "Наименование элемента")
    @NotBlank(message = "elementName should not be blank")
    private String elementName;
}