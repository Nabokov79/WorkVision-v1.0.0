package ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.standardSize.StandardSizeDto;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения информации об элементе к данным оборудования")
public class EquipmentElementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Индентификатор диагностируемого оборудования")
    private Long equipmentDiagnosedId;
    @Schema(description = "Индентификатор элемента типа оборудования")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента типа оборудования")
    private Long partElementId;
    @Schema(description = "Типоразмер элемента или подэлемента")
    private StandardSizeDto standardSize;
}