package ru.nabokovsg.gateway.controller.company;

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
import ru.nabokovsg.gateway.client.company.HeatSupplyAreaClient;
import ru.nabokovsg.gateway.dto.company.heatSupplyArea.NewHeatSupplyAreaDto;
import ru.nabokovsg.gateway.dto.company.heatSupplyArea.UpdateHeatSupplyAreaDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/area",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name = "Район теплоснабжения",
        description = "API для работы с данными района теплоснабжения")
public class HeatSupplyAreaController {

    private final HeatSupplyAreaClient client;

    @Operation(summary = "Добавление данных района теплоснабжения")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                                       @Parameter(description = "Район теплоснабжения") NewHeatSupplyAreaDto areaDto) {
        return client.save(areaDto);
    }

    @Operation(summary = "Изменение данных района теплоснабжения")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                                     @Parameter(description = "Район теплоснабжения") UpdateHeatSupplyAreaDto areaDto) {
        return client.update(areaDto);
    }

    @Operation(summary = "Получение данных района теплоснабжения")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получение кратких сведений о всех района теплоснабжения")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable @NotNull @Positive
                                             @Parameter(description = "Идентификатор филиала") Long id) {
        return client.getAll(id);
    }

    @Operation(summary = "Удаление данных района теплоснабжения")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive   @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}