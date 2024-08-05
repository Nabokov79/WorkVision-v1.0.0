package ru.nabokovsg.diagnosedEquipment.controller.equipmentType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypeElement.EquipmentTypeElementDto;
import ru.nabokovsg.diagnosedEquipment.dto.equipmentType.equipmentTypeElement.ResponseEquipmentTypeElementDto;
import ru.nabokovsg.diagnosedEquipment.service.equipmentType.EquipmentTypeElementService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/equipments/type/element",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Элемент",
        description="API для работы с данными элементов оборудования")
public class EquipmentTypeElementController {

    private final EquipmentTypeElementService service;

    @Operation(summary = "Добавление нового элемента")
    @PostMapping
    public ResponseEntity<ResponseEquipmentTypeElementDto> save(@RequestBody
                                                                @Parameter(description = "Элемент")
                                                                EquipmentTypeElementDto elementDto) {
        return ResponseEntity.ok().body(service.save(elementDto));
    }

    @Operation(summary = "Изменение данных элемента")
    @PatchMapping
    public ResponseEntity<ResponseEquipmentTypeElementDto> update(@RequestBody
                                                                  @Parameter(description = "Элемент")
                                                                  EquipmentTypeElementDto elementDto) {
        return ResponseEntity.ok().body(service.update(elementDto));
    }

    @Operation(summary = "Получить все элементы оборудования")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseEquipmentTypeElementDto>> getAll(
                                @PathVariable @Parameter(description = "Идентификатор оборудования") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }
}
