package ru.nabokovsg.laboratoryNK.dto.diagnosticDocument;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.laboratoryNK.model.DocumentStatus;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные документа по результатам проведенного обследования")
public class DiagnosticDocumentDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Дата обследования/выдачи документа")
    private LocalDate date;
    @Schema(description = "Диагностируемое оборудование")
    private String equipmentDiagnosed;
    @Schema(description = "Место установки оборудования")
    private String installationLocation;
    @Schema(description = "Вид документа диагностики")
    private String documentType;
    @Schema(description = "Номер документа")
    private Integer documentNumber;
    @Schema(description = "Рекомендуемая дата следующего обследования")
    private LocalDate nextDate;
    @Schema(description = "Статус документа")
    private DocumentStatus status;
    @Schema(description = "Путь к файлу документа")
    private String documentPath;
    @Schema(description = "Путь к файлу чертежа")
    private String drawingPath;
}