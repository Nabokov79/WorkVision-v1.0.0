package ru.nabokovsg.documentNK.dto.client.documentCreate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.documentNK.dto.client.templateCreate.LaboratoryCertificateDto;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Информация о сотруднике лаборатории НК")
public class LaboratoryEmployeeDto {

    @Schema(description = "Индентификатор")
    private Long id;
    @Schema(description = "Должность")
    private String post;
    @Schema(description = "Фамилия, инициалы")
    private String initials;
    @Schema(description = "Аттестация")
    private List<LaboratoryCertificateDto> certificate;
}