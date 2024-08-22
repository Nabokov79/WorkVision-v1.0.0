package ru.nabokovsg.diagnosedNK.dto.measurement.remarkByEquipment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Замечание по техническому состоянию элементов/подэлементов оборудования")
public class ResponseRemarkByEquipmentDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор оборудования")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Наименование подэлемента")
    private String partElementName;
    @Schema(description = "Замечание по техническому состоянию")
    private String inspection;
}