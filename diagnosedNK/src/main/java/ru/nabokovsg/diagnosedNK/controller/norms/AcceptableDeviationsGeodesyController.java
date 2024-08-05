package ru.nabokovsg.diagnosedNK.controller.norms;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.norms.geodesy.AcceptableDeviationsGeodesyDto;
import ru.nabokovsg.diagnosedNK.dto.norms.geodesy.ResponseAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.diagnosedNK.service.norms.AcceptableDeviationsGeodesyService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/norms/geodesy",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные допустимых значений при проведении геодезии(нивелирования)",
        description="API для работы с данными норм при проведении геодезии(нивелирования)")
public class AcceptableDeviationsGeodesyController {

    private final AcceptableDeviationsGeodesyService service;

    @Operation(summary = "Новая рекомендация для раздела отчета")
    @PostMapping
    public ResponseEntity<ResponseAcceptableDeviationsGeodesyDto> save(
            @RequestBody
            @Parameter(name = "Нормы по геодезии(нивелированию)") AcceptableDeviationsGeodesyDto geodesyDto) {
        return ResponseEntity.ok().body(service.save(geodesyDto));
    }

    @Operation(summary = "Изменение рекомендации")
    @PatchMapping
    public ResponseEntity<ResponseAcceptableDeviationsGeodesyDto> update(
            @RequestBody
            @Parameter(name = "Нормы по геодезии(нивелированию)") AcceptableDeviationsGeodesyDto geodesyDto) {
        return ResponseEntity.ok().body(service.update(geodesyDto));
    }

    @Operation(summary = "Получить рекомендации по типу объекта")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseAcceptableDeviationsGeodesyDto>> getAll(
            @PathVariable @Parameter(name = "Идентификатор типа оборудования") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удалить норму по геодезии")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Нормы по геодезии(нивелированию) успешно удалены.");
    }
}