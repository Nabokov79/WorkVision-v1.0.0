package ru.nabokovsg.company.dto.building;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.company.dto.address.ResponseAddressDto;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные котельной, цтп")
public class ShortResponseBuildingDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Тип строения")
    private String buildingType;
    @Schema(description = "Название")
    private String login;
    @Schema(description = "Адрес")
    private ResponseAddressDto address;
}