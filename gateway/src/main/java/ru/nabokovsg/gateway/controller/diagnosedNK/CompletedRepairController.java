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
import ru.nabokovsg.gateway.client.diagnosedNK.CompletedRepairClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.completedRepair.NewCompletedRepairDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/measurement/repair",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Выволненные ремонты элементов, подэлементов оборудования",
        description="API для работы с данными выполненных ремонтов элементов, подэлементов оборудования")
public class CompletedRepairController {

    private final CompletedRepairClient client;

    @Operation(summary = "Добавить выполненный ремонт элемента")
    @PostMapping
    public Mono<Object> save(
            @RequestBody @Valid @Parameter(description = "Выполненный ремонт") NewCompletedRepairDto repairDto) {
        return client.save(repairDto);
    }

    @Operation(summary = "Получить выполненные ремонты элементов оборудования" +
                        " по индентификатору записи рабочего журнала")
    @GetMapping("/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }

    @Operation(summary = "Удалить ремонт элемента")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}