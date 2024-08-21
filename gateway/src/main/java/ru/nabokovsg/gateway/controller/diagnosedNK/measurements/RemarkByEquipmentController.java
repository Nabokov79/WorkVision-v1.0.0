package ru.nabokovsg.gateway.controller.diagnosedNK.measurements;

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
import ru.nabokovsg.gateway.client.diagnosedNK.measurements.RemarkByEquipmentClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.remarkByEquipment.NewRemarkByEquipmentDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.remarkByEquipment.UpdateRemarkByEquipmentDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/measurement/equipment/remark",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные замечания по техническому состоянию элементов/подэлементов оборудования",
        description="API для работы с данными замечания по техническому состоянию элементов/подэлементов оборудования")
public class RemarkByEquipmentController {

    private final RemarkByEquipmentClient client;

    @Operation(summary = "Добавить данные визуального осмотра")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid  @Parameter(name = "Данные замечания")
                             NewRemarkByEquipmentDto remarkDto) {
        return client.save(remarkDto);
    }

    @Operation(summary = "Добавить данные визуального осмотра")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid @Parameter(name = "Данные замечания")
                               UpdateRemarkByEquipmentDto remarkDto) {
        return client.update(remarkDto);
    }

    @Operation(summary = "Получить данные визуального осмотра по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }
}