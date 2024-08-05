package ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypePartElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения информации о подэлементе")
public class ResponseEquipmentTypePartElementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование подэлемента")
    private String partElementName;
}