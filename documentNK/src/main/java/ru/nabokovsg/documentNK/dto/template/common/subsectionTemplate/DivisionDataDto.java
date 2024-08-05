package ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения данных структурного подразделения в подразделе документа")
public class DivisionDataDto {

    @Schema(description = "Тип структурного подразделения")
    private String divisionType;
    @Schema(description = "Индентификатор структурного подразделения организации")
    private Long divisionId;
    @Schema(description = "Пользовательское название структурного подразделения организации")
    private String userDivisionName;
    @Schema(description = "Указать адресс структурного подразделения")
    private Boolean specifyAddress;
    @Schema(description = "Указать лицензию/аттестацию структурного подразделения")
    private Boolean specifyCertificate;
}