package ru.nabokovsg.diagnosedNK.controller.measurement.hardnessMeasurement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.hardnessMeasurement.ResponseElementHardnessMeasurementDto;
import ru.nabokovsg.diagnosedNK.service.measurement.hardnessMeasurement.ElementHardnessMeasurementService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/measurement/hardness",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные измерений твердости металла оборудования",
        description="API для работы с данными измерений твердости металла оборудования")
public class HardnessMeasurementController {

    private final ElementHardnessMeasurementService service;

    @Operation(summary = "Добавить данные измерений твердости металла элементов, подэлементов оборудования")
    @PostMapping
    public ResponseEntity<ResponseElementHardnessMeasurementDto> save(@RequestBody
                                                                 @Parameter(name = "Данные измерений твердости металла")
                                                                 HardnessMeasurementDto measurementDto) {
        return ResponseEntity.ok().body(service.save(measurementDto));
    }

    @Operation(summary = "Получить данные данные измерений твердости металла элементов, подэлементов оборудования" +
                                                                          " по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseElementHardnessMeasurementDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удалить результат измерения твердости")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Результат измерения твердости успешно удален.");
    }
}