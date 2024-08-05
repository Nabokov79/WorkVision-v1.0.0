package ru.nabokovsg.diagnosedNK.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения рекомендации сотрудника")
public class RecommendationEmployeeDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор оборудования")
    private Long equipmentId;
    @Schema(description = "Рекомендация")
    private String recommendation;
}