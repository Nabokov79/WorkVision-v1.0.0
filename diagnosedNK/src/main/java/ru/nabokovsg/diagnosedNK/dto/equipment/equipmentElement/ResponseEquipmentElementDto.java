package ru.nabokovsg.diagnosedNK.dto.equipment.equipmentElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPartElement.ResponseEquipmentPartElementDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.standardSize.StandardSizeDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные элемента оборудования")
public class ResponseEquipmentElementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Подэлементы элемента")
    private List<ResponseEquipmentPartElementDto> partsElement;
    @Schema(description = "Типоразмер")
    private StandardSizeDto standardSize;
}