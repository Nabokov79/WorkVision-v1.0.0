package ru.nabokovsg.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.company.dto.heatSupplyArea.HeatSupplyAreaDto;
import ru.nabokovsg.company.dto.heatSupplyArea.ResponseHeatSupplyAreaDto;
import ru.nabokovsg.company.dto.heatSupplyArea.ShortResponseHeatSupplyAreaDto;
import ru.nabokovsg.company.service.HeatSupplyAreaService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/heat/supply/area",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Район теплоснабжения",
        description = "API для работы с данными района теплоснабжения")
public class HeatSupplyAreaController {

    private final HeatSupplyAreaService service;

    @Operation(summary = "Добавление данных района теплоснабжения")
    @PostMapping
    public ResponseEntity<ShortResponseHeatSupplyAreaDto> save(
                             @RequestBody @Parameter(description = "Район теплоснабжения") HeatSupplyAreaDto areaDto) {
        return ResponseEntity.ok().body(service.save(areaDto));
    }

    @Operation(summary = "Изменение данных района теплоснабжения")
    @PatchMapping
    public ResponseEntity<ShortResponseHeatSupplyAreaDto> update(
                            @RequestBody @Parameter(description = "Район теплоснабжения") HeatSupplyAreaDto areaDto) {
        return ResponseEntity.ok().body(service.update(areaDto));
    }

    @Operation(summary = "Получение данных района теплоснабжения")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseHeatSupplyAreaDto> get(
                                                    @PathVariable @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получение кратких сведений о всех района теплоснабжения")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ShortResponseHeatSupplyAreaDto>> getAll(
                                            @PathVariable @Parameter(description = "Идентификатор филиала") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удаление данных района теплоснабжения")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные района теплоснабжения удалены.");
    }
}