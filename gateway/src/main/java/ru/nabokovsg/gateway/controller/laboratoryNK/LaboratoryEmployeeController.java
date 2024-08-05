package ru.nabokovsg.gateway.controller.laboratoryNK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.laboratoryNK.LaboratoryEmployeeClient;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/employee",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Сотрудники лаборатории НК",
        description="API для работы с сервисом работы с данными сотрудников лаборатории НК")
public class LaboratoryEmployeeController {

    private final LaboratoryEmployeeClient client;

    @Operation(summary = "Добавление сотрудников")
    @GetMapping("/copy/{id}")
    public Flux<Object> copy(
              @PathVariable @NotNull @Positive
              @Parameter(description = "Индентификатор структурного подразделения") Long id
            , @RequestParam(name = "divisionType") @NotBlank
              @Parameter(description = "Тип структурного подразделения") String divisionType) {
        return client.copy(id, divisionType);
    }

    @Operation(summary = "Получение данных сотрудника")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(description = "Индентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получение данных всех сотрудников")
    @GetMapping
    public Flux<Object> getAll() {
        return client.getAll();
    }

    @Operation(summary = "Удаление данных сотрудника")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Индентификатор") Long id) {
        return client.delete(id);
    }
}