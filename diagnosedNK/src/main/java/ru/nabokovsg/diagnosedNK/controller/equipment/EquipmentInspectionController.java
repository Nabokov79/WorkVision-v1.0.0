package ru.nabokovsg.diagnosedNK.controller.equipment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentInspection.EquipmentInspectionDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentInspection.ResponseEquipmentInspectionDto;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentInspectionService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/equipments/inspection",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные выполненных обследований оборудования",
        description="API для работы с данными обследований оборудования")
public class EquipmentInspectionController {

    private final EquipmentInspectionService service;

    @Operation(summary = "Добавить данные обследования оборудования")
    @PostMapping
    public ResponseEntity<ResponseEquipmentInspectionDto> save(@RequestBody
                                                               @Parameter(name = "Обследование оборудования")
                                                               EquipmentInspectionDto inspectionDto) {
        return ResponseEntity.ok().body(service.save(inspectionDto));
    }

    @Operation(summary = "Изменить данные обследования оборудования")
    @PatchMapping
    public ResponseEntity<ResponseEquipmentInspectionDto> update(@RequestBody
                                                                 @Parameter(name = "Обследование оборудования")
                                                                 EquipmentInspectionDto inspectionDto) {
        return ResponseEntity.ok().body(service.update(inspectionDto));
    }

    @Operation(summary = "Получить данные всех обследований оборудования")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseEquipmentInspectionDto>> getAll(
            @PathVariable @Parameter(name = "Идентификатор диагностируемого оборудования") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удалить измеренный обследования")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные обследования успешно удалены.");
    }
}