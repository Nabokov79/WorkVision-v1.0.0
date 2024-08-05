package ru.nabokovsg.gateway.controller.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.company.EmployeeClient;
import ru.nabokovsg.gateway.dto.company.employee.NewEmployeeDto;
import ru.nabokovsg.gateway.dto.company.employee.UpdateEmployeeDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/employee",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Сотрудники",
     description="API для работы с данными сотрудников")
public class EmployeeController {

    private final EmployeeClient client;

    @Operation(summary = "Добавление данных нового сотрудника")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(description = "Сотрудник") NewEmployeeDto employeeDto) {
        return client.save(employeeDto);
    }

    @Operation(summary = "Изменение данных сотрудника")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                                   @Parameter(description = "Сотрудник") UpdateEmployeeDto employeeDto) {
        return client.update(employeeDto);
    }

    @Operation(summary = "Получение данных сотрудника")
    @GetMapping("/{id}")
    public Mono<Object>ge(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получение данных всех сотрудников")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable @NotNull @Positive
                                       @Parameter(description = "Идентификатор структурного подразделения") Long id,
                                       @RequestParam(name = "divisionType") @NotBlank
                                       @Parameter(description = "Тип структурного подразделения") String divisionType) {
        return client.getAll(id, divisionType);
    }

    @Operation(summary = "Удаление данных сотрудника")
    @DeleteMapping("/{id}")
    public Mono<String> deleteEmployee(@PathVariable @NotNull @Positive
                                           @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}