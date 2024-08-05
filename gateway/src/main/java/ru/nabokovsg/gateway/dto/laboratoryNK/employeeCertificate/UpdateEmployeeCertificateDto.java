package ru.nabokovsg.gateway.dto.laboratoryNK.employeeCertificate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для изменения информации об аттестации сотрудника")
public class UpdateEmployeeCertificateDto {

    @Schema(description = "Индентификатор")
    @NotNull(message = "employee certificate id should not be null")
    @Positive(message = "employee certificate id can only be positive")
    private Long id;
    @Schema(description = "Тип документа")
    @NotBlank(message = "document type should not be blank")
    private String documentType;
    @Schema(description = "Номер сертификата")
    @NotBlank(message = "certificate number should not be blank")
    private String certificateNumber;
    @Schema(description = "Вид контроля соглано сертификата")
    @NotBlank(message = "control type should not be blank")
    private String controlType;
    @Schema(description = "Квалификационный уровень сотрудника по данным из сертификата")
    @NotBlank(message = "level should not be blank")
    private String level;
    @Schema(description = "Дата выдачи сертификата специализированной организацией")
    @NotNull(message = "start date should not be null")
    private LocalDate startDate;
    @Schema(description = "Дата окончания действия сертификата")
    @NotNull(message = "end date should not be null")
    private LocalDate endDate;
    @Schema(description = "Шифр объектов, для контроля которых допущен сотрудник согласно данным сертификата")
    @NotBlank(message = "points should not be blank")
    private String points;
    @Schema(description = "Организация, выдавшая сертификат")
    @NotBlank(message = "organization should not be blank")
    private String organization;
    @Schema(description = "Индентификатор сотрудника, которому принадлежит сертификат")
    @NotNull(message = "employee id should not be null")
    @Positive(message = "employee id can only be positive")
    private Long employeeId;
}