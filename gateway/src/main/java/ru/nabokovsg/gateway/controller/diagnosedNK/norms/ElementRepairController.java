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
import ru.nabokovsg.gateway.client.diagnosedNK.norms.ElementRepairClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.elementRepair.NewElementRepairDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.elementRepair.UpdateElementRepairDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/norms/repair",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Ремонт элементов оборудования",
        description="API для работы с данными ремонта элементов оборудования")
public class ElementRepairController {

    private final ElementRepairClient client;

    @Operation(summary = "Добавление способа ремонта")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(description = "Тип ремонта элемента")
                                                                    NewElementRepairDto repairDto) {
        return client.save(repairDto);
    }

    @Operation(summary = "Изменение данных способа ремонта")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid @Parameter(description = "Тип ремонта элемента")
                                                                  UpdateElementRepairDto repairDto) {
        return client.update(repairDto);
    }

    @Operation(summary = "Получить типы ремонта элементов оборудования по его типу")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получить типы ремонта элементов оборудования по его типу")
    @GetMapping
    public Flux<Object> getAll() {
        return client.getAll();
    }

    @Operation(summary = "Удалить тип ремонта")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}