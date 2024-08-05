package ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypeElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения элемента оборудования")
public class EquipmentTypeElementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор оборудования")
    private Long equipmentTypeId;
    @Schema(description = "Наименование элемента")
    private String elementName;
}