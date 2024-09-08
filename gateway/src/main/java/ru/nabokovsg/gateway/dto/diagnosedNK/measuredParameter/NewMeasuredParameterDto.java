package ru.nabokovsg.gateway.dto.diagnosedNK.measuredParameter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления измеряемого параметра")
public class NewMeasuredParameterDto {

    @Schema(description = "Наименование параметра")
    @NotBlank(message = "parameterName should not be blank")
    private String parameterName;
    @Schema(description = "Единица измерения параметра")
    @NotBlank(message = "unitMeasurement should not be blank")
    private String unitMeasurement;
}