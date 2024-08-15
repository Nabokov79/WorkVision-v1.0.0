package ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.visualMeasuringSurvey;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.ResponseShortCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.identifiedDefect.ResponseIdentifiedDefectDto;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные визуального и измерительноко контроля при проведении обследования элемента'")
public class ResponseCalculatedVMSurveyDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор элемента")
    private Long elementId;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Данные визуального и измерительноко контроля подэлементов")
    private Set<ResponseExaminedPartElementDto> examinedPartElements;
    @Schema(description = "Выявленные дефекты элемента")
    private Set<ResponseIdentifiedDefectDto> identifiedDefects;
    @Schema(description = "Выявленные места ремонта элемента")
    private Set<ResponseShortCompletedRepairDto> completedRepairs;
    @Schema(description = "Выявленные замечания по элементу")
    private String inspection;
}