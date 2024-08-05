package ru.nabokovsg.laboratoryNK.dto.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные района теплоснабжения")
public class HeatSupplyAreaDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Полное наименование")
    private String fullName;
    @Schema(description = "Краткое наименование")
    private String shortName;
    @Schema(description = "Эксплуатационные участки")
    private List<ExploitationRegionDto> exploitationRegions;
}