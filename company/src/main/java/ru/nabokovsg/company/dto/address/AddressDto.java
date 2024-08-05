package ru.nabokovsg.company.dto.address;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для добавления/изменения информации об адресе")
public class AddressDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Почтовый индекс")
    private Integer index;
    @Schema(description = "Населенный пункт")
    @NotBlank(message = "city should not be blank")
    private String city;
    @Schema(description = "Название улицы")
    private String street;
    @Schema(description = "Номер дома")
    private String houseNumber;
    @Schema(description = "Номер корпуса дома")
    private String buildingNumber;
    @Schema(description = "Литера дома")
    private String letter;
}