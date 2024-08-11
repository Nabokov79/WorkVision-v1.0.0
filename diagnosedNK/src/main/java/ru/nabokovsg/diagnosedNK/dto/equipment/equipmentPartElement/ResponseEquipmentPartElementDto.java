package ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPartElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.equipment.standardSize.StandardSizeDto;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные о подэлементе")
public class ResponseEquipmentPartElementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование подэлемента")
    private String partElementName;
    @Schema(description = "Типоразмер")
    private StandardSizeDto standardSize;
}