package ru.nabokovsg.diagnosedNK.dto.equipment.equipmentElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.equipment.standardSize.StandardSizeDto;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения информации об элементе к данным оборудования")
public class EquipmentElementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор диагностируемого оборудования")
    private Long equipmentDiagnosedId;
    @Schema(description = "Идентификатор элемента типа оборудования")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента типа оборудования")
    private Long partElementId;
    @Schema(description = "Типоразмер элемента или подэлемента")
    private StandardSizeDto standardSize;
}