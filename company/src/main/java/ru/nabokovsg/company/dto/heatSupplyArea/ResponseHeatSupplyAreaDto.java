package ru.nabokovsg.company.dto.heatSupplyArea;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.company.dto.exploitationRegion.ResponseExploitationRegionDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные района теплоснабжения")
public class ResponseHeatSupplyAreaDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Полное наименование")
    private String fullName;
    @Schema(description = "Краткое наименование")
    private String shortName;
    @Schema(description = "Эксплуатационный участок")
    private List<ResponseExploitationRegionDto> exploitationRegions;
}