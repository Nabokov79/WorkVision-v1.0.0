package ru.nabokovsg.gateway.controller.diagnosedEquipment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.diagnosedEquipment.EquipmentTypeClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentType.NewEquipmentTypeDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentType.UpdateEquipmentTypeDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/equipments/type",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name = "Тип оборудования",
        description = "API для работы с данными типа оборудования")
public class EquipmentTypeController {

    private final EquipmentTypeClient client;

    @Operation(summary = "Добавление нового типа оборудования")
    @PostMapping
    public Mono<Object> save(
            @RequestBody @Valid @Parameter(description = "Тип оборудования") NewEquipmentTypeDto equipmentTypeDto) {
        return client.save(equipmentTypeDto);
    }

    @Operation(summary = "Изменение данных типа оборудования")
    @PatchMapping
    public Mono<Object> update(
            @RequestBody @Valid @Parameter(description = "Тип оборудования") UpdateEquipmentTypeDto equipmentTypeDto) {
        return client.update(equipmentTypeDto);
    }

    @Operation(summary = "Получить тип оборудования")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получить все типы оборудования")
    @GetMapping("/all")
    public Flux<Object> getAll() {
        return client.getAll();
    }

    @Operation(summary = "Удаление типа оборудования")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}