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
@Schema(description = "Данные для изменения замечания по техническому состоянию элементов/подэлементов оборудования")
public class UpdateRemarkByEquipmentDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Результат визуального осмотра элемента(подэлемента")
    @NotBlank(message = "inspection should not be blank")
    private String inspection;
}