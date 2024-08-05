package ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Полные данные оборудования")
public class ResponseEquipmentDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор типа оборудования")
    private Long equipmentTypeId;
    @Schema(description = "Полное наименование")
    private String equipmentName;
    @Schema(description = "Стационарный номер")
    private Integer stationaryNumber;
    @Schema(description = "Объем")
    private Integer volume;
    @Schema(description = "Старое или новое оборудование")
    private Boolean old;
    @Schema(description = "Модель")
    private String model;
    @Schema(description = "Колличество мест проведения измерений геодезии")
    private Integer geodesyLocations;
}