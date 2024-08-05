package ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.visualMeasuringSurvey;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair.ResponseShortCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect.ResponseShortIdentifiedDefectDto;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные визуального и измерительноко контроля при проведении подэлемента")
public class ResponseExaminedPartElementDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Наименование подэлемента")
    private String partElementName;
    @Schema(description = "Выявленные дефекты элемента")
    private Set<ResponseShortIdentifiedDefectDto> identifiedDefects;
    @Schema(description = "Выявленные места ремонта элемента")
    private Set<ResponseShortCompletedRepairDto> completedRepairs;
    @Schema(description = "Выявленные замечания по элементу")
    private String inspection;
}