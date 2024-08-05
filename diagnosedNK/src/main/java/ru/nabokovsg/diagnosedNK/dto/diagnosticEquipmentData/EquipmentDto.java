package ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Полные данные оборудования")
public class EquipmentDto {

    @Schema(description = "Идентификатор")
    private Long id;
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
    @Schema(description = "Количество мест проведения измерений геодезии")
    private Integer geodesyLocations;
    @Schema(description = "Элементы оборудования")
    private List<ElementDto> elements;
}