package ru.nabokovsg.gateway.dto.laboratoryNK.workJournal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для изменения информации об обследовании объектов тепловых сетей")
public class UpdateWorkJournalDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "survey journal id should not be blank")
    @Positive(message = "survey journal id must be positive")
    private Long id;
    @Schema(description = "Дата обследования")
    private LocalDate date;
    @Schema(description = "Идентификатор филиала организации")
    @NotNull(message = "branch id should not be null")
    @Positive(message = "branch id must be positive")
    private Long branchId;
    @Schema(description = "Идентификатор района теплоснабжения")
    @NotNull(message = "heat supply area id should not be null")
    @Positive(message = "heat supply area id must be positive")
    private Long heatSupplyAreaId;
    @Schema(description = "Адрес")
    @NotBlank(message = "address should not be blank")
    private String address;
    @Schema(description = "Наименование оборудования")
    @NotBlank(message = "equipmentName should not be blank")
    private String equipmentName;
    @Schema(description = "Тип выполняемой работы")
    @NotBlank(message = "work type should not be blank")
    private String workType;
    @Schema(description = "Основание проведения работы(адресная программа, заявка, распоряжение руководителя)")
    private String taskSource;
    @Schema(description = "Комментарий")
    private String comment;
    @Schema(description = "Идентификатор руководителя работ")
    @NotNull(message = "chef id should not be blank")
    @Positive(message = "chef id must be positive")
    private Long chiefId;
    @Schema(description = "Идентификаторы сотрудников лаборатории назначенных для выполнения задачи")
    @NotNull(message = "laboratory employee ids should not be null")
    @NotEmpty(message = "laboratory employee ids should not be empty")
    private List<Long> laboratoryEmployeesIds;
    @Schema(description = "Идентификатор типа документа диагностики")
    @NotNull(message = "diagnostic document type id should not be null")
    @Positive(message = "diagnostic document type id should not be positive")
    private Long diagnosticDocumentTypeId;
    @Schema(description = "Необходимо приложить чертеж")
    @NotNull(message = "drawing should not be null")
    private boolean drawing;
}