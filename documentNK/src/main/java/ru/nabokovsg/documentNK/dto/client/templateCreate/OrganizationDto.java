package ru.nabokovsg.documentNK.dto.client.templateCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Данные организации")
public class OrganizationDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Полное наименование")
    private String fullName;
    @Schema(description = "Краткое наименование")
    private String shortName;
    @Schema(description = "Адрес")
    private AddressDto address;
}