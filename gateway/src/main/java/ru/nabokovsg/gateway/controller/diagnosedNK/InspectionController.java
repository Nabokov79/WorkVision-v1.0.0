package ru.nabokovsg.gateway.controller.diagnosedNK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.diagnosedNK.InspectionClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.inspection.NewInspectionDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/measurement/survey/inspection",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные визуального осмотра элемента(подэлемента) оборудования",
        description="API для работы с данными визуального осмотра элемента(подэлемента) оборудования")
public class InspectionController {

    private final InspectionClient client;

    @Operation(summary = "Добавить данные визуального осмотра")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(name = "Данные визуального осмотра")
                                                                 NewInspectionDto inspectionDto) {
        return client.save(inspectionDto);
    }

    @Operation(summary = "Получить данные визуального осмотра по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }
}