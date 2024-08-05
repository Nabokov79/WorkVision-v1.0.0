package ru.nabokovsg.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.company.dto.employee.EmployeeDto;
import ru.nabokovsg.company.dto.employee.ResponseEmployeeDto;
import ru.nabokovsg.company.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/employee",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Сотрудники",
     description="API для работы с данными сотрудников")
public class EmployeeController {

    private final EmployeeService service;

    @Operation(summary = "Добавление данных нового сотрудника")
    @PostMapping
    public ResponseEntity<ResponseEmployeeDto> save(@RequestBody
                                                 @Parameter(description = "Сотрудник") EmployeeDto employeeDto) {
        return ResponseEntity.ok().body(service.save(employeeDto));
    }

    @Operation(summary = "Изменение данных сотрудника")
    @PatchMapping
    public ResponseEntity<ResponseEmployeeDto> update(@RequestBody
                                                  @Parameter(description = "Сотрудник") EmployeeDto employeeDto) {
        return ResponseEntity.ok().body(service.update(employeeDto));
    }

    @Operation(summary = "Получение данных сотрудника")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseEmployeeDto> get(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получение данных всех сотрудников")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseEmployeeDto>> getAll(
                                       @PathVariable(required = false)
                                       @Parameter(description = "Идентификатор структурного подразделения") Long id,
                                       @RequestParam(name = "divisionType", required = false)
                                       @Parameter(description = "Тип структурного подразделения") String divisionType,
                                       @RequestParam(name = "employeeId", required = false)
                                       @Parameter(description = "Идентификаторы сотрудников") List<Long> ids) {
        return ResponseEntity.ok().body(service.getAll(id, divisionType, ids));
    }

    @Operation(summary = "Удаление данных сотрудника")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные сотрудника удалены.");
    }
}