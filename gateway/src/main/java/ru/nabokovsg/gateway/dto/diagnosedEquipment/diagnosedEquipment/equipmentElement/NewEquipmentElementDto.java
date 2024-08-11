package ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentElement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.standardSize.NewStandardSizeDto;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления информации об элементе к данным оборудования")
public class NewEquipmentElementDto {

    @Schema(description = "Идентификатор диагностируемого оборудования")
    @NotNull(message = "equipmentDiagnosed id should not be null")
    @Positive(message = "equipmentDiagnosed id can only be positive")
    private Long equipmentDiagnosedId;
    @Schema(description = "Идентификатор элемента типа оборудования")
    @NotNull(message = "element id should not be null")
    @Positive(message = "element id can only be positive")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента типа оборудования")
    private Long partElementId;
    @Schema(description = "Типоразмер элемента или подэлемента")
    private NewStandardSizeDto standardSize;
}