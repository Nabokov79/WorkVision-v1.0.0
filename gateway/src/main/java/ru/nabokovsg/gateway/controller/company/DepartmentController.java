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
import ru.nabokovsg.gateway.client.company.DepartmentClient;
import ru.nabokovsg.gateway.dto.company.department.NewDepartmentDto;
import ru.nabokovsg.gateway.dto.company.department.UpdateDepartmentDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/department",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Подразделение филиала организации",
        description="API для работы с данными подразделения филиала организации")
public class DepartmentController {

    private final DepartmentClient client;

    @Operation(summary = "Добавление данных подразделения филиала организации")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                                       @Parameter(description = "Подразделение") NewDepartmentDto departmentDto) {
        return client.save(departmentDto);
    }

    @Operation(summary = "Изменение данных подразделения филиала организации")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                                         @Parameter(description = "Подразделение") UpdateDepartmentDto departmentDto) {
        return client.update(departmentDto);
    }

    @Operation(summary = "Получение данных подразделения филиала организации")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получение кратких сведений о подразделении филиала")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable @NotNull @Positive
                               @Parameter(description = "Идентификатор филиала") Long id) {
        return client.getAll(id);
    }

    @Operation(summary = "Удаление данных подразделения филиала")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}