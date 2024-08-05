package ru.nabokovsg.documentNK.dto.client.documentCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные измеренного дефекта")
public class IdentifiedDefectDto {

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
    @Schema(description = "Наименование дефекта")
    private String defectName;
    @Schema(description = "Порядковый номер сварного соединения")
    private Integer weldedJointNumber;
    @Schema(description = "Указание недопустимого дефекта")
    private Boolean notMeetRequirements;
    @Schema(description = "Рассчитанные параметры дефекта")
    private Set<ParameterMeasurementDto> parameterMeasurements;
}