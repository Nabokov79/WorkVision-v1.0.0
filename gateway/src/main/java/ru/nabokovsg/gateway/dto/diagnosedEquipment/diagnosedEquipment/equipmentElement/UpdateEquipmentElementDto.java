package ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentElement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.standardSize.UpdateStandardSizeDto;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для изменения информации об элементе к данным оборудования")
public class UpdateEquipmentElementDto {

    @Schema(description = "Индентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Индентификатор диагностируемого оборудования")
    @NotNull(message = "equipmentDiagnosed id should not be null")
    @Positive(message = "equipmentDiagnosed id can only be positive")
    private Long equipmentDiagnosedId;
    @Schema(description = "Индентификатор элемента типа оборудования")
    @NotNull(message = "element id should not be null")
    @Positive(message = "element id can only be positive")
    private Long elementId;
    @Schema(description = "Индентификатор подэлемента типа оборудования")
    private Long partElementId;
    @Schema(description = "Типоразмер")
    private UpdateStandardSizeDto standardSize;
}