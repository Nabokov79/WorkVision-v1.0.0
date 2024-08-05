package ru.nabokovsg.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.company.dto.branch.BranchDto;
import ru.nabokovsg.company.dto.branch.ResponseBranchDto;
import ru.nabokovsg.company.dto.branch.ShortResponseBranchDto;
import ru.nabokovsg.company.service.BranchService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/branch",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Филиал организации",
        description="API для работы с данными филиала")
public class BranchController {

    private final BranchService service;

    @Operation(summary = "Добавление данных филиала")
    @PostMapping
    public ResponseEntity<ShortResponseBranchDto> save(@RequestBody @Parameter(description = "Филиал") BranchDto branchDto) {
        return ResponseEntity.ok().body(service.save(branchDto));
    }

    @Operation(summary = "Изменение данных филиала")
    @PatchMapping
    public ResponseEntity<ShortResponseBranchDto> update(@RequestBody @Parameter(description = "Филиал") BranchDto branchDto) {
        return ResponseEntity.ok().body(service.update(branchDto));
    }

    @Operation(summary = "Получить данные филиала во его идентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseBranchDto> get(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получение сведений о филиалах")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ShortResponseBranchDto>> getAll(@PathVariable
                                                       @Parameter(description = "Идентификатор организации") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удаление данных филиала")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Филиал успешно удален.");
    }
}