package ru.nabokovsg.diagnosedNK.controller.measurement.ultrasonicControl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicControl.ResponseUltrasonicMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicControl.UltrasonicMeasurementDto;
import ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicControl.UltrasonicControlService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/measurement/ultrasonic",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные ультразвукового контроля сварного соединения",
        description="API для работы с данными ультразвукового контроля сварного соединения")
public class UltrasonicControlController {

    private final UltrasonicControlService service;

    @Operation(summary = "Добавить данные ультразвукового контроля")
    @PostMapping
    public ResponseEntity<ResponseUltrasonicMeasurementDto> save(@RequestBody
                                                            @Parameter(name = "Данные ультразвукового контроля")
                                                            UltrasonicMeasurementDto measurementDto) {
        return ResponseEntity.ok().body(service.save(measurementDto));
    }

    @Operation(summary = "Добавить данные измеренного дефекта")
    @PatchMapping
    public ResponseEntity<ResponseUltrasonicMeasurementDto> update(@RequestBody
                                                            @Parameter(name = "Данные ультразвукового контроля")
                                                            UltrasonicMeasurementDto measurementDto) {
        return ResponseEntity.ok().body(service.update(measurementDto));
    }

    @Operation(summary = "Получить данные ультразвукового контроля по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseUltrasonicMeasurementDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор записи рабочего журнала") Long workJournalId) {
        return ResponseEntity.ok().body(service.getAll(workJournalId));
    }

    @Operation(summary = "Удалить данные ультразвукового контроля")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные ультразвукового контроля успешно удалены.");
    }
}