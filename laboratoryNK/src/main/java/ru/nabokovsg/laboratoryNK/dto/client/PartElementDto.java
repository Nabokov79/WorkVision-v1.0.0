package ru.nabokovsg.laboratoryNK.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные подэлемента")
public class PartElementDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование подэлемента")
    private String partName;
    @Schema(description = "Типоразмер")
    private StandardSizeDto standardSize;
}