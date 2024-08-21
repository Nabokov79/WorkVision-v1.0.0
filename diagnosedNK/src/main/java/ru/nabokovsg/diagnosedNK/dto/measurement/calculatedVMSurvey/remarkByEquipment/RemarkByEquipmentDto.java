package ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.remarkByEquipment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения замечания по техническому состоянию элементов/подэлементов оборудования")
public class RemarkByEquipmentDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор диагностируемого оборудования")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Результат визуального осмотра элемента(подэлемента")
    private String inspection;
}