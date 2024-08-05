package ru.nabokovsg.diagnosedNK.controller.norms;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.norms.elementRepair.ElementRepairDto;
import ru.nabokovsg.diagnosedNK.dto.norms.elementRepair.ResponseElementRepairDto;
import ru.nabokovsg.diagnosedNK.dto.norms.elementRepair.ResponseShortElementRepairDto;
import ru.nabokovsg.diagnosedNK.service.norms.ElementRepairService;

import java.util.List;

@RestController
@RequestMapping(
        value = "diagnosed/nk/norms/repair",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Ремонт элементов оборудования",
        description="API для работы с данными ремонта элементов оборудования")
public class ElementRepairController {

    private final ElementRepairService service;

    @Operation(summary = "Добавление способа ремонта")
    @PostMapping
    public ResponseEntity<ResponseElementRepairDto> save(
                             @RequestBody @Parameter(description = "Тип ремонта элемента") ElementRepairDto repairDto) {
        return ResponseEntity.ok().body(service.save(repairDto));
    }

    @Operation(summary = "Изменение данных способа ремонта")
    @PatchMapping
    public ResponseEntity<ResponseElementRepairDto> update(
                             @RequestBody @Parameter(description = "Тип ремонта элемента") ElementRepairDto repairDto) {
        return ResponseEntity.ok().body(service.update(repairDto));
    }

    @Operation(summary = "Получить типы ремонта элементов оборудования по его типу")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseElementRepairDto> get(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить типы ремонта элементов оборудования по его типу")
    @GetMapping
    public ResponseEntity<List<ResponseShortElementRepairDto>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Удалить тип ремонта")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Тип ремонта элемента оборудования успешно удален.");
    }
}