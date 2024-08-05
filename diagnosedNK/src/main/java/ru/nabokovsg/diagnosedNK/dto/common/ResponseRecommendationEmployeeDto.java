package ru.nabokovsg.diagnosedNK.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные рекомендации сотрудника")
public class ResponseRecommendationEmployeeDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор записи рабочего журнала")
    private Long workJournalId;
    @Schema(description = "Идентификатор оборудования")
    private Long equipmentId;
    @Schema(description = "Рекомендация")
    private String recommendation;
}