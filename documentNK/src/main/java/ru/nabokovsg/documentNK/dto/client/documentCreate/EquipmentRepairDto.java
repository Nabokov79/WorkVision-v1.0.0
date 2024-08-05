package ru.nabokovsg.documentNK.dto.client.documentCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные обследования оборудования")
public class EquipmentRepairDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Дата выполнения ремонта")
    private String date;
    @Schema(description = "Описание выполненного ремонта")
    private String description;
    @Schema(description = "Организация, выполнившая ремонта")
    private String organization;
}