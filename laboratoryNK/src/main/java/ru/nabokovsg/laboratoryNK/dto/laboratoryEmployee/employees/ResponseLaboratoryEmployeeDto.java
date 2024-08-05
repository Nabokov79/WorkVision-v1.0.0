package ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employees;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate.ResponseEmployeeCertificateDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Полная информация о сотруднике лаборатории НК")
public class ResponseLaboratoryEmployeeDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Должность")
    private String post;
    @Schema(description = "Фамилия, инициалы")
    private String initials;
    @Schema(description = "Аттестация")
    private List<ResponseEmployeeCertificateDto> certificate;
}