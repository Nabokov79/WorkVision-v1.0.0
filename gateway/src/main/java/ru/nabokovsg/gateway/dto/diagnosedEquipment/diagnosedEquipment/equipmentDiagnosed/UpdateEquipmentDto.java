package ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentDiagnosed;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для изменения информации об оборудовании")
public class UpdateEquipmentDto {

    @Schema(description = "Индентификатор")
    @NotNull(message = "document header id should not be null")
    @Positive(message = "document header id can only be positive")
    private Long id;
    @Schema(description = "Индентификатор котельной, цтп")
    @NotNull(message = "building id should not be null")
    @Positive(message = "building id can only be positive")
    private Long buildingId;
    @Schema(description = "Индентификатор типа оборудования")
    @NotNull(message = "equipmentType id should not be null")
    @Positive(message = "equipmentType id can only be positive")
    private Long equipmentTypeId;
    @Schema(description = "Стационарный номер")
    private Integer stationaryNumber;
    @Schema(description = "Старый или новый бак-аккумулятор")
    private Boolean old;
    @Schema(description = "Производственное помещение")
    private String room;
    @Schema(description = "Колличество мест проведения измерений геодезии")
    private Integer geodesyLocations;
}