package ru.nabokovsg.diagnosedEquipment.controller.diagnosedEquipment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement.EquipmentElementDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentElement.ResponseEquipmentElementDto;
import ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment.EquipmentElementService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/equipments/element",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Элемент",
        description="API для работы с данными элементов оборудования")
public class EquipmentElementController {

    private final EquipmentElementService service;

    @Operation(summary = "Добавление нового элемента")
    @PostMapping
    public ResponseEntity<ResponseEquipmentElementDto> save(
                                      @RequestBody @Parameter(description = "Элемент") EquipmentElementDto elementDto) {
        return ResponseEntity.ok().body(service.save(elementDto));
    }

    @Operation(summary = "Изменение данных элемента")
    @PatchMapping
    public ResponseEntity<ResponseEquipmentElementDto> update(
                                      @RequestBody @Parameter(description = "Элемент") EquipmentElementDto elementDto) {
        return ResponseEntity.ok().body(service.update(elementDto));
    }

    @Operation(summary = "Получить все элементы диагностируемого оборудования")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseEquipmentElementDto>> getAll(
                                @PathVariable @Parameter(description = "Идентификатор оборудования") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }
}