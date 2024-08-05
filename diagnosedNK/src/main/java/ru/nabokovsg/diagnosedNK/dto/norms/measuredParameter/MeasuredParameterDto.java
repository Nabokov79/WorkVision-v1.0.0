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
@Schema(description = "Данные для добавления/ихмерения сведений об измеряемом параметре")
public class MeasuredParameterDto {

    @Schema(description = "Иднентификатор")
    private Long id;
    @Schema(description = "Наименование параметра")
    private String measuredParameter;
    @Schema(description = "Единица измерения параметра")
    private String unitMeasurement;
    @Schema(description = "Требуемые вычисления параметров дефекта")
    private String calculation;
}