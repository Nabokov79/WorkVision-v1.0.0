package ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentPartElement.ResponseEquipmentPartElementDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.standardSize.StandardSizeDto;

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