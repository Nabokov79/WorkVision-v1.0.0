package ru.nabokovsg.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.company.dto.building.BuildingDto;
import ru.nabokovsg.company.dto.building.ResponseBuildingDto;
import ru.nabokovsg.company.dto.building.ShortResponseBuildingDto;
import ru.nabokovsg.company.service.BuildingService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/building",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Котельная(ЦТП)",
        description="API для работы с информацией о котельной(цтп)")
public class BuildingController {

    private final BuildingService service;

    @Operation(summary = "Добавление новой информации о котельной(цтп)")
    @PostMapping
    public ResponseEntity<ShortResponseBuildingDto> save(
                                            @RequestBody @Parameter(description = "Строение") BuildingDto buildingDto) {
        return ResponseEntity.ok().body(service.save(buildingDto));
    }

    @Operation(summary = "Изменение данных котельной(цтп)")
    @PatchMapping
    public ResponseEntity<ShortResponseBuildingDto> update(
                                            @RequestBody @Parameter(description = "Строение") BuildingDto buildingDto) {
        return ResponseEntity.ok().body(service.update(buildingDto));
    }

    @Operation(summary = "Получение данных всех котельных(цтп)")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBuildingDto> get(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получение данных всех котельных(цтп)")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ShortResponseBuildingDto>> getAll(
                    @PathVariable @Parameter(description = "Идентификатор эксплуатационного участка") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удаление данных котельной(цтп)")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные котельной(ЦТП) успешно удалены.");
    }
}