package ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.parameterMeasurement.ResponseParameterMeasurementDto;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Выполненый ремонт элемента, подэлемента оборудования для добавления к документу")
public class ResponseShortCompletedRepairDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Наименование ремонта")
    private String repairName;
    @Schema(description = "Рассчитанные параметры выполненного ремонта элемента")
    private Set<ResponseParameterMeasurementDto> parameterMeasurements;
}