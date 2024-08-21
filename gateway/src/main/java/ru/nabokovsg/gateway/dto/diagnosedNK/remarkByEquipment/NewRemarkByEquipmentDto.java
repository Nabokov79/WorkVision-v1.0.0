package ru.nabokovsg.gateway.dto.diagnosedNK.remarkByEquipment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные добавления замечания по техническому состоянию элементов/подэлементов оборудования")
public class NewRemarkByEquipmentDto {

    @Schema(description = "Идентификатор оборудования")
    @NotNull(message = "equipmentId should not be null")
    @Positive(message = "equipmentId can only be positive")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    @NotNull(message = "elementId should not be null")
    @Positive(message = "elementId can only be positive")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Результат визуального осмотра элемента(подэлемента")
    @NotBlank(message = "inspection should not be blank")
    private String inspection;
}