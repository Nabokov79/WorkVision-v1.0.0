package ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.identifiedDefect;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.parameterMeasurement.ResponseParameterMeasurementDto;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные измеренного дефекта для добавления к документу")
public class ResponseShortIdentifiedDefectDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Наименование дефекта")
    private String defectName;
    @Schema(description = "Указание недопустимого дефекта")
    private Boolean notMeetRequirements;
    @Schema(description = "Рассчитанные параметры дефекта")
    private Set<ResponseParameterMeasurementDto> parameterMeasurements;
}