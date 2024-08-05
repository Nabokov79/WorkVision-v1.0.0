package ru.nabokovsg.documentNK.dto.client.documentCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Выполненый ремонт элемента, подэлемента оборудования")
public class CompletedRepairDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор элемента")
    private Long elementId;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Индентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Наименование подэлемента")
    private String partName;
    @Schema(description = "Наименование ремонта")
    private String repairName;
    @Schema(description = "Рассчитанные параметры выполненного ремонта элемента")
    private Set<ParameterMeasurementDto> parameterMeasurements;
}