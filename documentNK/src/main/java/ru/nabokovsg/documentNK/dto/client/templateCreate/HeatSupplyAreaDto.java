package ru.nabokovsg.documentNK.dto.client.templateCreate;

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

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Полное наименование")
    private String fullName;
    @Schema(description = "Краткое наименование")
    private String shortName;
    @Schema(description = "Эксплуатационный участок")
    private List<ExploitationRegionDto> exploitationRegions;
}