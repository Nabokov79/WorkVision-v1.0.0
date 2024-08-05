package ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentPartElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.standardSize.StandardSizeDto;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные о подэлементе")
public class ResponseEquipmentPartElementDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Наименование подэлемента")
    private String partName;
    @Schema(description = "Типоразмер")
    private StandardSizeDto standardSize;
}