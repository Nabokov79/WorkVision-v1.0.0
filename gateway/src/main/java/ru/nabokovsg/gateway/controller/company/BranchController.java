package ru.nabokovsg.gateway.controller.company;

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
import ru.nabokovsg.gateway.client.company.BranchClient;
import ru.nabokovsg.gateway.dto.company.branch.NewBranchDto;
import ru.nabokovsg.gateway.dto.company.branch.UpdateBranchDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/branch",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Valid
@Tag(name="Филиал организации",
        description="API для работы с данными филиала")
public class BranchController {

    private final BranchClient client;

    @Operation(summary = "Добавление данных филиала")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(description = "Филиал") NewBranchDto branchDto) {
        return client.save(branchDto);
    }

    @Operation(summary = "Изменение данных филиала")
    @PatchMapping
    public Mono<Object> updateBranch(@RequestBody @Valid @Parameter(description = "Филиал") UpdateBranchDto branchDto) {
        return client.update(branchDto);
    }

    @Operation(summary = "Получение данных филиала")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получение сведений о филиалах")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable @NotNull @Positive
                               @Parameter(description = "Идентификатор организации") Long id) {
        return client.getAll(id);
    }

    @Operation(summary = "Удаление данных филиала")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}