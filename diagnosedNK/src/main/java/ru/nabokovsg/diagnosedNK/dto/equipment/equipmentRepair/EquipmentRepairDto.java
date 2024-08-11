package ru.nabokovsg.diagnosedNK.dto.equipment.equipmentRepair;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения информации об ремонте оборудования")
public class EquipmentRepairDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Дата выполнения ремонта")
    private String date;
    @Schema(description = "Описание выполненного ремонта")
    private String description;
    @Schema(description = "Организация, выполнившая ремонт")
    private String organization;
    @Schema(description = "Идентификатор диагностируемого оборудования")
    private Long equipmentDiagnosedId;
}