package ru.nabokovsg.documentNK.dto.client.documentCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные визуального и измерительноко контроля при проведении обследования подэлемента")
public class ExaminedPartElementDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Наименование подэлемента")
    private String partElementName;
    @Schema(description = "Выявленные дефекты элемента")
    private Set<IdentifiedDefectDto> identifiedDefects;
    @Schema(description = "Выявленные места ремонта элемента")
    private Set<CompletedRepairDto> completedRepairs;
    @Schema(description = "Выявленные замечания по элементу")
    private String inspection;
}