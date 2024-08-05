package ru.nabokovsg.documentNK.dto.client.documentCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные визуального и измерительноко контроля при проведении обследования элемента")
public class VisualMeasuringSurveyDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор элемента")
    private Long elementId;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Данные визуального и измерительноко контроля подэлементов")
    private Set<ExaminedPartElementDto> examinedPartElements;
    @Schema(description = "Выявленные дефекты элемента")
    private Set<IdentifiedDefectDto> identifiedDefects;
    @Schema(description = "Выявленные места ремонта элемента")
    private Set<CompletedRepairDto> completedRepairs;
    @Schema(description = "Выявленные замечания по элементу")
    private String inspection;
}