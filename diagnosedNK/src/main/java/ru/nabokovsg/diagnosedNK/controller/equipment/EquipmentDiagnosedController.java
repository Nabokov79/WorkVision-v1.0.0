package ru.nabokovsg.diagnosedNK.controller.equipment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentDiagnosed.EquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentDiagnosed.ResponseEquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentDiagnosed.ResponseShortEquipmentDto;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentDiagnosedService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/equipments",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Оборудование",
        description="API для работы с данными оборудования котельных, ЦТП")
public class EquipmentDiagnosedController {

    private final EquipmentDiagnosedService service;

    @Operation(summary = "Добавление данных оборудования")
    @PostMapping
    public ResponseEntity<ResponseEquipmentDto> save(@RequestBody
                                                 @Parameter(description = "Оборудование") EquipmentDto equipmentDto) {
        return ResponseEntity.ok().body(service.save(equipmentDto));
    }

    @Operation(summary = "Изменение данных оборудования")
    @PatchMapping
    public ResponseEntity<ResponseEquipmentDto> update(@RequestBody
                                                   @Parameter(description = "Оборудование") EquipmentDto equipmentDto) {
        return ResponseEntity.ok().body(service.update(equipmentDto));
    }

    @Operation(summary = "Получить оборудование")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseEquipmentDto> get(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить все оборудование котельной, ЦТП")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseShortEquipmentDto>> getAll(@PathVariable
                                                    @Parameter(description = "Идентификатор котельной, ЦТП") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удаление оборудования")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Оборудование успешно удалено.");
    }
}