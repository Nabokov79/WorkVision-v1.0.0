package ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения информации о аттестации сотрудника")
public class EmployeeCertificateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Тип документа")
    private String documentType;
    @Schema(description = "Номер сертификата")
    private String certificateNumber;
    @Schema(description = "Вид контроля согласно сертификата")
    private String controlType;
    @Schema(description = "Квалификационный уровень сотрудника по данным из сертификата")
    private String level;
    @Schema(description = "Дата выдачи сертификата специализированной организацией")
    private LocalDate startDate;
    @Schema(description = "Дата окончания действия сертификата")
    private LocalDate endDate;
    @Schema(description = "Шифр объектов, для контроля которых допущен сотрудник согласно данным сертификата")
    private String points;
    @Schema(description = "Организация, выдавшая сертификат")
    private String organization;
    @Schema(description = "Идентификатор сотрудника, которому принадлежит сертификат")
    private Long employeeId;
}