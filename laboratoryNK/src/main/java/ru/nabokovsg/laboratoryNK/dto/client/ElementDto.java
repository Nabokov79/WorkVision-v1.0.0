package ru.nabokovsg.laboratoryNK.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные элемента оборудования")
public class ElementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование")
    private String elementName;
    @Schema(description = "Данные подэлементов")
    private Set<PartElementDto> partsElement;
    @Schema(description = "Типоразмер")
    private StandardSizeDto standardSize;
}