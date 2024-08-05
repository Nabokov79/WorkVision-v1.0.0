package ru.nabokovsg.laboratoryNK.dto.workJournal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employees.ShortResponseLaboratoryEmployeeDto;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные выполняемой работе")
public class ResponseWorkJournalDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор типа оборудования")
    private Long equipmentTypeId;
    @Schema(description = "Идентификатор диагностируемого оборудования")
    private Long equipmentId;
    @Schema(description = "Дата обследования")
    private LocalDate date;
    @Schema(description = "Полное наименование филиала организации")
    private String branch;
    @Schema(description = "Место проведения работ")
    private String placeWork;
    @Schema(description = "Адрес")
    private String address;
    @Schema(description = "Диагностируемое оборудование")
    private String equipmentDiagnosed;
    @Schema(description = "Тип выполняемой работы")
    private String workType;
    @Schema(description = "Основание проведения работы(адресная программа, заявка, распоряжение руководителя)")
    private String taskSource;
    @Schema(description = "Комментарий")
    private String comment;
    @Schema(description = "Сотрудник(и) лаборатории назначенные для выполнения задачи")
    private Set<ShortResponseLaboratoryEmployeeDto> employees;
}