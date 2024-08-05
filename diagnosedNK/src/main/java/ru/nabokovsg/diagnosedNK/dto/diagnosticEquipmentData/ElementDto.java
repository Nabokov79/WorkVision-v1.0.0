package ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные элемента оборудования")
public class ElementDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор елемента типа оборудования")
    private Long elementId;
    @Schema(description = "Наименование")
    private String elementName;
    @Schema(description = "Данные подэлементов")
    private Set<PartElementDto> partsElement;
    @Schema(description = "Типоразмер")
    private StandardSizeDto standardSize;
}