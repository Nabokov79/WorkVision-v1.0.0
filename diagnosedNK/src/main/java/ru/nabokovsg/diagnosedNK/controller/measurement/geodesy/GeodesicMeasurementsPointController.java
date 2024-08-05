package ru.nabokovsg.diagnosedNK.controller.measurement.geodesy;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.GeodesicMeasurementsPointDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseGeodesicMeasurementsPointDto;
import ru.nabokovsg.diagnosedNK.service.measurement.geodesy.GeodesicMeasurementsPointService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/measurement/geodesy",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные измерений при выполнении геодезической съемки(нивелировании) оборудования",
        description="API для работы с данными измерений при выполнении геодезической съемки(нивелировании) оборудования")
public class GeodesicMeasurementsPointController {

    private final GeodesicMeasurementsPointService service;

    @Operation(summary = "Добавить данные геодезический съемки оборудования")
    @PostMapping
    public ResponseEntity<List<ResponseGeodesicMeasurementsPointDto>> save(@RequestBody
                                                         @Parameter(name = "Данные измерений геодезической съемки")
                                                                     GeodesicMeasurementsPointDto measurementDto) {
        return ResponseEntity.ok().body(service.save(measurementDto));
    }

    @Operation(summary = "Получить данные геодезических измерений по идентификатору записи журнала задач")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseGeodesicMeasurementsPointDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удалить результаты геодезической съемки")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Геодезическое измерение успешно удалено.");
    }
}