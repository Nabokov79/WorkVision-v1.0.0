package ru.nabokovsg.gateway.controller.diagnosedNK;

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
import ru.nabokovsg.gateway.client.diagnosedNK.UltrasonicThicknessElementMeasurementClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.ultrasonicThicknessMeasurement.NewUltrasonicThicknessMeasurementDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/measurement/survey/ut",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные ультразвуковой толщинометрии элементов, подэлементов оборудования",
        description="API для работы с данными ультразвуковой толщинометрии элементов, подэлементов оборудования")
public class UltrasonicThicknessElementMeasurementController {

    private final UltrasonicThicknessElementMeasurementClient client;

    @Operation(summary = "Добавить ультразвуковой толщинометрии")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(name = "Данные измерения толщины")
                                          NewUltrasonicThicknessMeasurementDto measurementDto) {
        return client.save(measurementDto);
    }

    @Operation(summary = "Получить данные ультразвуковой толщинометрии")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }

    @Operation(summary = "Удалить данные ультразвуковой толщинометрии")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}