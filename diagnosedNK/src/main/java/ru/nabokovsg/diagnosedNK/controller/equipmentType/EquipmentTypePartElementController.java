package ru.nabokovsg.diagnosedNK.controller.equipmentType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypePartElement.EquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedNK.dto.equipmentType.equipmentTypePartElement.ResponseEquipmentTypePartElementDto;
import ru.nabokovsg.diagnosedNK.service.equipmentType.EquipmentTypePartElementService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/equipments/type/element/part",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Подэлемент",
        description="API для работы с данными подэлементов элементов")
public class EquipmentTypePartElementController {

    private final EquipmentTypePartElementService service;

    @Operation(summary = "Добавление нового подэлемента")
    @PostMapping
    public ResponseEntity<ResponseEquipmentTypePartElementDto> save(
            @RequestBody @Parameter(description = "Подэлемент") EquipmentTypePartElementDto partElementDto) {
        return ResponseEntity.ok().body(service.save(partElementDto));
    }

    @Operation(summary = "Изменение данных подэлемента")
    @PatchMapping
    public ResponseEntity<ResponseEquipmentTypePartElementDto> update(
            @RequestBody @Parameter(description = "Подэлемент") EquipmentTypePartElementDto partElementDto) {
        return ResponseEntity.ok().body(service.update(partElementDto));
    }

    @Operation(summary = "Получить все подэлементы элементы")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseEquipmentTypePartElementDto>> getAll(
            @PathVariable @Parameter(description = "Идентификатор элемента") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }
}