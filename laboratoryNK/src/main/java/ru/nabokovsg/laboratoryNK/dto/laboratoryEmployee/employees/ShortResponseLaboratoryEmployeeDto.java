package ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employees;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Краткая информация о сотруднике лаборатории НК")
public class ShortResponseLaboratoryEmployeeDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Должность")
    private String post;
    @Schema(description = "Фамилия, инициалы")
    private String initials;
}