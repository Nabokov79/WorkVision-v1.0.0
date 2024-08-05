package ru.nabokovsg.diagnosedNK.controller.measurement.ultrasonicThicknessMeasurement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.ResponseUltrasonicThicknessElementMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessMeasurementDto;
import ru.nabokovsg.diagnosedNK.service.measurement.ultrasonicThicknessMeasurement.UltrasonicThicknessElementMeasurementService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/ut",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные ультразвуковой толщинометрии элементов, подэлементов оборудования",
        description="API для работы с данными ультразвуковой толщинометрии элементов, подэлементов оборудования")
public class UltrasonicThicknessElementMeasurementController {

    private final UltrasonicThicknessElementMeasurementService service;

    @Operation(summary = "Добавить ультразвуковой толщинометрии")
    @PostMapping
    public ResponseEntity<ResponseUltrasonicThicknessElementMeasurementDto> save(
                                                             @RequestBody @Parameter(name = "Данные измерения толщины")
                                                             UltrasonicThicknessMeasurementDto measurementDto) {
        return ResponseEntity.ok().body(service.save(measurementDto));
    }

    @Operation(summary = "Получить данные ультразвуковой толщинометрии")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseUltrasonicThicknessElementMeasurementDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Индентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удалить данные ультразвуковой толщинометрии")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные ультразвуковой толщинометрии успешно удалены.");
    }
}