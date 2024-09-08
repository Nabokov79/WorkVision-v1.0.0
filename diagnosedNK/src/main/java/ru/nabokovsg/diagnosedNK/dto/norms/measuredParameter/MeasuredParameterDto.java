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
@Schema(description = "Данные для добавления/измерения сведений об измеряемом параметре")
public class MeasuredParameterDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование параметра")
    private String parameterName;
    @Schema(description = "Единица измерения параметра")
    private String unitMeasurement;
}