package ru.nabokovsg.company.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения информации о подразделении")
public class DepartmentDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Полное наименование подразделения")
    private String fullName;
    @Schema(description = "Краткое наименование подразделения")
    private String shortName;
    @Schema(description = "Идентификатор адреса")
    private Long addressId;
    @Schema(description = "Идентификатор филиала")
    private Long branchId;
}