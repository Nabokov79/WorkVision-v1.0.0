package ru.nabokovsg.laboratoryNK.dto.laboratoryCertificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения информации об аттестации лаборатории")
public class LaboratoryCertificateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор структурного подразделения организации")
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