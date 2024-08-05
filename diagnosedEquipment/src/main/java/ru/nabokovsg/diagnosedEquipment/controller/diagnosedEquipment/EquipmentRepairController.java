package ru.nabokovsg.diagnosedEquipment.controller.diagnosedEquipment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentRepair.EquipmentRepairDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentRepair.ResponseEquipmentRepairDto;
import ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment.EquipmentRepairService;
import java.util.List;

@RestController
@RequestMapping(
        value = "/equipments/repair",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные выполненного ремонта диагностируемого оборудования",
        description="API для работы с данными ремонта диагностируемого оборудования")
public class EquipmentRepairController {

    private final EquipmentRepairService service;

    @Operation(summary = "Добавить данные выполненного ремонта оборудования")
    @PostMapping
    public ResponseEntity<ResponseEquipmentRepairDto> save(@RequestBody
                                                           @Parameter(name = "Выполненный ремонт")
                                                           EquipmentRepairDto repairDto) {
        return ResponseEntity.ok().body(service.save(repairDto));
    }

    @Operation(summary = "Изменить данные выполненного ремонта оборудования")
    @PatchMapping
    public ResponseEntity<ResponseEquipmentRepairDto> update(@RequestBody
                                                             @Parameter(name = "Выполненный ремонт")
                                                             EquipmentRepairDto repairDto) {
        return ResponseEntity.ok().body(service.update(repairDto));
    }

    @Operation(summary = "Получить данные всех выполненных ремонтов оборудования")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseEquipmentRepairDto>> getAll(
            @PathVariable @Parameter(name = "Идентификатор диагностируемого оборудования") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удалить данные ремонта оборудования")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные ремонта успешно удалены.");
    }
}