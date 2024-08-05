package ru.nabokovsg.diagnosedNK.dto.measurement.geodesy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Рассчитанные данные результатов выполнения геодезии(нивелировании)")
public class ResponseEquipmentGeodesicMeasurementsDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Рассчитанные результаты измерения по реперам")
    private Set<ResponseReferencePointDto> referencePoints;
    @Schema(description = "Рассчитанные результаты измерения по контрольным точкам")
    private Set<ResponseControlPointDto> controlPoints;
    @Schema(description = "Рассчитанные результаты по соседним и противоположным точкам")
    private Set<ResponsePointDifferenceDto> pointDifferences;
}