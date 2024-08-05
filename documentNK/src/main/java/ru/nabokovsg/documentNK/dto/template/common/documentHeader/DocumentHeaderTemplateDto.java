package ru.nabokovsg.documentNK.dto.template.common.documentHeader;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления/изменения заголовка документа")
public class DocumentHeaderTemplateDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор типа документа диагностики")
    private Long documentTypeId;
    @Schema(description = "Тип структурного подразделения организации")
    private String divisionType;
    @Schema(description = "Идентификатор структурного подразделения организации")
    private Long divisionId;
    @Schema(description = "Идентификатор сотрудника структурного подразделения")
    private Long employeeId;
    @Schema(description = "Указать в документе полное наименование")
    private Boolean specifyFullName;
    @Schema(description = "Указать адрес")
    private Boolean specifyAddress;
    @Schema(description = "Указать лицензию/аттестацию")
    private Boolean specifyLicense;
    @Schema(description = "Указать контактные данные организации")
    private Boolean specifyContacts;
}