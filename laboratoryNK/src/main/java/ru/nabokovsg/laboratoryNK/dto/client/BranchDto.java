package ru.nabokovsg.laboratoryNK.dto.client;

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

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Полное название")
    private String fullName;
    @Schema(description = "Краткое название")
    private String shortName;
    @Schema(description = "Адрес")
    private AddressDto address;
    @Schema(description = "Подразделения")
    private List<ShortDepartmentDto> departments;
    @Schema(description = "Подразделения")
    private List<HeatSupplyAreaDto> heatSupplyAreas;
    @Schema(description = "Эксплуатационные участки")
    private List<ExploitationRegionDto> exploitationRegions;
}