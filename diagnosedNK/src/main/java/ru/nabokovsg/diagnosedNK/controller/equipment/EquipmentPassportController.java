package ru.nabokovsg.diagnosedNK.controller.equipment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPassport.EquipmentPassportDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPassport.ResponseEquipmentPassportDto;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentPassportService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/equipments/passport",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Паспорт оборудования",
        description="API для работы с данными паспорта оборудования")
public class EquipmentPassportController {

    private final EquipmentPassportService service;

    @Operation(summary = "Добавление данных паспорта")
    @PostMapping
    public ResponseEntity<ResponseEquipmentPassportDto> save(@RequestBody
                                               @Parameter(description = "Паспорт") EquipmentPassportDto passportDto) {
        return ResponseEntity.ok().body(service.save(passportDto));
    }

    @Operation(summary = "Изменение данных паспорта")
    @PatchMapping
    public ResponseEntity<ResponseEquipmentPassportDto> update(@RequestBody
                                                 @Parameter(description = "Паспорт") EquipmentPassportDto passportDto) {
        return ResponseEntity.ok().body(service.update(passportDto));
    }

    @Operation(summary = "Получить данные паспорта оборудования")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseEquipmentPassportDto>> getAll(
                                       @PathVariable(name = "id")
                                       @Parameter(description = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удаление данных паспорта")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные паспорта успешно удалены.");
    }
}