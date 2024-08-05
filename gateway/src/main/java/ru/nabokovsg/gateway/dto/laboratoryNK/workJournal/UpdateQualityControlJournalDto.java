package ru.nabokovsg.gateway.dto.laboratoryNK.workJournal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для изменения информации о выполненное работе по контролю качества")
public class UpdateQualityControlJournalDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "survey journal id should not be blank")
    @Positive(message = "survey journal id must be positive")
    private Long id;
    @Schema(description = "Дата обследования")
    private LocalDate date;
    @Schema(description = "Идентификатор филиала организации")
    @NotNull(message = "branch id should not be blank")
    @Positive(message = "branch id must be positive")
    private Long branchId;
    @Schema(description = "Идентификатор района теплоснабжения")
    @NotNull(message = "heat supply area id should not be blank")
    @Positive(message = "heat supply area id must be positive")
    private Long heatSupplyAreaId;
    @Schema(description = "Идентификатор эксплуатационного участка")
    @NotNull(message = "exploitation region id should not be blank")
    @Positive(message = "exploitation region id must be positive")
    private Long exploitationRegionId;
    @Schema(description = "Место проведения работ")
    @NotNull(message = "address id should not be blank")
    @Positive(message = "address id must be positive")
    private Long addressId;
    @Schema(description = "Наименование оборудования")
    @NotBlank(message = "equipmentName should not be blank")
    private String equipmentName;
    @Schema(description = "Тип выполняемой работы")
    @NotBlank(message = "work type should not be blank")
    private String workType;
    @Schema(description = "Комментарий к задаче")
    private String comment;
    @Schema(description = "Идентификатор руководителя работ")
    @NotNull(message = "chef id should not be blank")
    @Positive(message = "chef id must be positive")
    private Long chiefId;
    @Schema(description = "Идентификаторы сотрудника лаборатории, выполнившего контроль")
    @NotNull(message = "laboratory employee id should not be null")
    @NotEmpty(message = "laboratory employee id should not be empty")
    private Long laboratoryEmployeesId;
    @Schema(description = "Идентификатор типа документа диагностики")
    @NotNull(message = "diagnostic document type id should not be null")
    @Positive(message = "diagnostic document type id should not be positive")
    private Long diagnosticDocumentTypeId;
    @Schema(description = "необходимо приложить чертеж")
    @NotNull(message = "drawing should not be null")
    private boolean drawing;
}