package ru.nabokovsg.company.dto.branch;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения информации о филиале")
public class BranchDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор организации")
    private Long organizationId;
    @Schema(description = "Полное название")
    private String fullName;
    @Schema(description = "Краткое название")
    private String shortName;
    @Schema(description = "Идентификатор адреса")
    private Long addressId;
}