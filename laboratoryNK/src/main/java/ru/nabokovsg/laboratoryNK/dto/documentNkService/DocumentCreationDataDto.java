package ru.nabokovsg.laboratoryNK.dto.documentNkService;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employees.ResponseLaboratoryEmployeeDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.ResponseMeasuringToolDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные для создания документа")
public class DocumentCreationDataDto {

    @Schema(description = "Идентификатор документа диагностики")
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
    @Schema(description = "Название документа")
    private String title;
    @Schema(description = "Заголовок документа")
    private String subtitle;
    @Schema(description = "Идентификатор типа документа")
    private Long typeDocumentId;
    @Schema(description = "Номер документа")
    private Integer documentNumber;
    @Schema(description = "Руководитель работ, начальник лаборатории")
    private ResponseLaboratoryEmployeeDto chief;
    @Schema(description = "Сотрудник(и) лаборатории назначенные для выполнения задачи")
    private Set<ResponseLaboratoryEmployeeDto> employees;
    @Schema(description = "Средства и приборы измерения и контроля сотрудников, выполнивших задачу")
    private List<ResponseMeasuringToolDto> measuringTools;
}