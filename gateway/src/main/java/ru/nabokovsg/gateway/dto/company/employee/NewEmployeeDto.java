package ru.nabokovsg.gateway.dto.company.employee;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные нового сотрудника")
public class NewEmployeeDto {

    @Schema(description = "Имя")
    @NotBlank(message = "name should not be blank")
    private String name;
    @Schema(description = "Отчество")
    @NotBlank(message = "patronymic should not be blank")
    private String patronymic;
    @Schema(description = "Фамилия")
    @NotBlank(message = "surname should not be blank")
    private String surname;
    @Schema(description = "Должность")
    @NotBlank(message = "post should not be blank")
    private String post;
    @Schema(description = "Идентификатор структурного подразделения")
    @NotNull(message = "division id should not be blank")
    @Positive(message = "division id must be positive")
    private Long divisionId;
    @Schema(description = "Тип структурного подразделения")
    @NotBlank(message = "division type should not be blank")
    private String divisionType;
    @Schema(description = "Номер телефона")
    @NotBlank(message = "phone should not be blank")
    private String phone;
    @Schema(description = "Факс")
    private String fax;
    @Schema(description = "электронная почта")
    @NotBlank(message = "email should not be blank")
    @Email(message = "email invalid")
    private String email;
}