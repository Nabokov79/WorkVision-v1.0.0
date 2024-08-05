package ru.nabokovsg.documentNK.dto.client.documentCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные обследования оборудования")
public class EquipmentInspectionDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Дата проведения обследования")
    private String date;
    @Schema(description = "Описание выполненного обследования")
    private String inspection;
    @Schema(description = "Номер документа, выданного по результатам обследования")
    private String documentNumber;
    @Schema(description = "Организация, выполнившая обследование")
    private String organization;
}