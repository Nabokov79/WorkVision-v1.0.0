package ru.nabokovsg.diagnosedNK.dto.measurement.identifiedDefect;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ResponseParameterMeasurementDto;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные измеренного дефекта")
public class ResponseIdentifiedDefectDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Наименование подэлемента")
    private String partName;
    @Schema(description = "Наименование дефекта")
    private String defectName;
    @Schema(description = "Указание недопустимого дефекта")
    private Boolean notMeetRequirements;
    @Schema(description = "Рассчитанные параметры дефекта")
    private Set<ResponseParameterMeasurementDto> parameterMeasurements;
}