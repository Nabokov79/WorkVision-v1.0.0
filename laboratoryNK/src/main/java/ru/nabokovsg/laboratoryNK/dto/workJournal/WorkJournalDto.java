package ru.nabokovsg.laboratoryNK.dto.workJournal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Данные для добавления/изменения информации об выполняемой работе")
public class WorkJournalDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Дата обследования")
    private LocalDate date;
    @Schema(description = "Идентификатор филиала организации")
    private Long branchId;
    @Schema(description = "Идентификатор района теплоснабжения")
    private Long heatSupplyAreaId;
    @Schema(description = "Идентификатор эксплуатационного участка")
    private Long exploitationRegionId;
    @Schema(description = "Идентификатор адреса")
    private Long addressId;
    @Schema(description = "Адрес")
    private String address;
    @Schema(description = "Идентификатор диагностируемого оборудования")
    private Long equipmentId;
    @Schema(description = "Наименование оборудования(для работ по контролю качества " +
            "сварных соединений  и обследованию объектов тепловых сетей)")
    @NotBlank(message = "equipmentName should not be blank")
    private String equipmentName;
    @Schema(description = "Тип выполняемой работы")
    private String workType;
    @Schema(description = "Основание проведения работы(адресная программа, заявка, распоряжение руководителя)")
    private String taskSource;
    @Schema(description = "Комментарий к задаче")
    private String comment;
    @Schema(description = "Идентификатор руководителя работ")
    private Long chiefId;
    @Schema(description = "Идентификаторы сотрудников лаборатории назначенных для выполнения задачи")
    private List<Long> laboratoryEmployeesIds;
    @Schema(description = "Идентификатор типа документа диагностики")
    private Long diagnosticDocumentTypeId;
    @Schema(description = "Необходимо приложить чертеж")
    private boolean drawing;
    @Schema(description = "Оборудование с теплоносителем или без него")
    private Boolean full;
}