package ru.nabokovsg.gateway.controller.diagnosedNK.norms;

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
import ru.nabokovsg.gateway.client.diagnosedNK.norms.AcceptableDeviationsGeodesyClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.geodesyNorm.NewAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.geodesyNorm.UpdateAcceptableDeviationsGeodesyDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/norms/geodesy",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные допустимых значений при проведении геодезии(нивелирования)",
        description="API для работы с данными норм при проведении геодезии(нивелирования)")
public class AcceptableDeviationsGeodesyController {

    private final AcceptableDeviationsGeodesyClient client;

    @Operation(summary = "Новая рекомендация для раздела отчета")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                             @Parameter(name = "Нормы по геодезии(нивелированию)")
                                     NewAcceptableDeviationsGeodesyDto geodesyDto) {
        return client.save(geodesyDto);
    }

    @Operation(summary = "Изменение рекомендации")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                               @Parameter(name = "Нормы по геодезии(нивелированию)")
                                    UpdateAcceptableDeviationsGeodesyDto geodesyDto) {
        return client.update(geodesyDto);
    }

    @Operation(summary = "Получить рекомендации по типу объекта")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор типа оборудования") Long equipmentTypeId) {
        return client.getAll(equipmentTypeId);
    }

    @Operation(summary = "Удалить норму по геодезии")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}