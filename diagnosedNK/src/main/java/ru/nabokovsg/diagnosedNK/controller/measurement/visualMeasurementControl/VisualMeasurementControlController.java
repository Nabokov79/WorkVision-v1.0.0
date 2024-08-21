package ru.nabokovsg.diagnosedNK.controller.measurement.visualMeasurementControl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl.ResponseVisualMeasurementControlDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementControl.VisualMeasurementControlDto;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected.VisualMeasurementControlService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/measurement/control/visual",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные замечания по техническому состоянию элементов/подэлементов оборудования",
        description="API для работы с данными замечания по техническому состоянию элементов/подэлементов оборудования")
public class VisualMeasurementControlController {

    private final VisualMeasurementControlService service;

    @Operation(summary = "Добавить данные результата измерения")
    @PostMapping
    public ResponseEntity<ResponseVisualMeasurementControlDto> save(@RequestBody
                                                                    @Parameter(name = "Данные измеренного дефекта")
                                                                    VisualMeasurementControlDto defectDto) {
        return ResponseEntity.ok().body(service.save(defectDto));
    }

    @Operation(summary = "Изменить данные результата измерения")
    @PatchMapping
    public ResponseEntity<ResponseVisualMeasurementControlDto> update(@RequestBody
                                                                      @Parameter(name = "Данные измеренного дефекта")
                                                                       VisualMeasurementControlDto defectDto) {
        return ResponseEntity.ok().body(service.update(defectDto));
    }

    @Operation(summary = "Получить данные измеренных дефектов элементов(подэлементов) оборудования" +
            " по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseVisualMeasurementControlDto>> getAll(@PathVariable(name = "id")
                               @Parameter(name = "Идентификатор записи рабочего журнала") Long workJournalId) {
        return ResponseEntity.ok().body(service.getAll(workJournalId));
    }

    @Operation(summary = "Удалить измеренный дефект")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Дефект успешно удален.");
    }
}