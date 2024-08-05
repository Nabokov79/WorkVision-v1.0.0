package ru.nabokovsg.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.company.dto.department.DepartmentDto;
import ru.nabokovsg.company.dto.department.ResponseDepartmentDto;
import ru.nabokovsg.company.dto.department.ShortResponseDepartmentDto;
import ru.nabokovsg.company.service.DepartmentService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/department",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Подразделение филиала организации",
        description="API для работы с данными подразделения филиала организации")
public class DepartmentController {

    private final DepartmentService service;

    @Operation(summary = "Добавление данных подразделения филиала организации")
    @PostMapping
    public ResponseEntity<ShortResponseDepartmentDto> save(@RequestBody
                                                @Parameter(description = "Подразделение") DepartmentDto departmentDto) {
        return ResponseEntity.ok().body(service.save(departmentDto));
    }

    @Operation(summary = "Изменение данных подразделения филиала организации")
    @PatchMapping
    public ResponseEntity<ShortResponseDepartmentDto> update(@RequestBody
                                                @Parameter(description = "Подразделение") DepartmentDto departmentDto) {
        return ResponseEntity.ok().body(service.update(departmentDto));
    }

    @Operation(summary = "Получение данных подразделения филиала организации")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDepartmentDto> get(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получение кратких сведений о подразделении филиала")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ShortResponseDepartmentDto>> getAll(@PathVariable
                                               @Parameter(description = "Идентификатор филиала") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удаление данных подразделения филиала")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Подразделение успешно удалено.");
    }
}