package ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Полные данные оборудования")
public class ResponseShortEquipmentDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Полное наименование")
    private String equipmentName;
    @Schema(description = "Стационарный номер")
    private Integer stationaryNumber;
    @Schema(description = "Объем")
    private Integer volume;
    @Schema(description = "Модель")
    private String model;
}