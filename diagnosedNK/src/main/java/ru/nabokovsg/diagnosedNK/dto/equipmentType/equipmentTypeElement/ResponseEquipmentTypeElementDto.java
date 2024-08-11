package ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypeElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypePartElement.ResponseEquipmentTypePartElementDto;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные элемента оборудования")
public class ResponseEquipmentTypeElementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование")
    private String elementName;
    @Schema(description = "Данные подэлементов")
    private Set<ResponseEquipmentTypePartElementDto> partsElement;
}