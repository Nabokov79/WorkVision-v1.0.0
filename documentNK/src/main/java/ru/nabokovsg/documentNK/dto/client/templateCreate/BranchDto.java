package ru.nabokovsg.documentNK.dto.client.templateCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные филиала")
public class BranchDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Полное название")
    private String fullName;
    @Schema(description = "Краткое название")
    private String shortName;
    @Schema(description = "Адрес")
    private AddressDto address;
    @Schema(description = "Подразделения")
    private List<DepartmentDto> departments;
    @Schema(description = "Подразделения")
    private List<HeatSupplyAreaDto> heatSupplyAreas;
}