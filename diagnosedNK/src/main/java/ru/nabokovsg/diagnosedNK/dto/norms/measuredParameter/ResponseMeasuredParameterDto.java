package ru.nabokovsg.diagnosedNK.dto.norms.measuredParameter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Измеряемый параметр")
public class ResponseMeasuredParameterDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Наименование параметра")
    private String parameterName;
    @Schema(description = "Единица измерения параметра")
    private String unitMeasurement;
    @Schema(description = "Требуемые вычисления параметров дефекта")
    private String calculation;
}