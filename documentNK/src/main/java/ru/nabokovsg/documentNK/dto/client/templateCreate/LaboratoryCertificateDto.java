package ru.nabokovsg.documentNK.dto.client.templateCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные аттестации лаборатории")
public class LaboratoryCertificateDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Индентификатор структурного подразделения организации")
    private Long divisionId;
    @Schema(description = "Вид выданного документа")
    private String documentType;
    @Schema(description = "Номер документа")
    private String licenseNumber;
    @Schema(description = "Дата начала действия документа")
    private LocalDate startDate;
    @Schema(description = "Дата окончания действия документа")
    private LocalDate endDate;
    @Schema(description = "Организация, выдавшая документ")
    private String organization;
}