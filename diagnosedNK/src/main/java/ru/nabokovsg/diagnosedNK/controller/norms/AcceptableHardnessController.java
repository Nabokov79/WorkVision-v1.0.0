package ru.nabokovsg.diagnosedNK.controller.norms;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableHardness.AcceptableHardnessDto;
import ru.nabokovsg.diagnosedNK.dto.norms.acceptableHardness.ResponseAcceptableHardnessDto;
import ru.nabokovsg.diagnosedNK.service.norms.AcceptableHardnessService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/norms/hardness",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные допустимых твердости металла элементов оборудования",
        description="API для работы с данными норм допустимой твердости металла элементов оборудования")
public class AcceptableHardnessController {

    private final AcceptableHardnessService service;

    @Operation(summary = "Добавить новые данные допустимой твердости металла элементов оборудования")
    @PostMapping
    public ResponseEntity<ResponseAcceptableHardnessDto> save(
            @RequestBody @Parameter(name = "Данные допустимой твердости металла") AcceptableHardnessDto hardnessDto) {
        return ResponseEntity.ok().body(service.save(hardnessDto));
    }

    @Operation(summary = "Изменение данных допустимой твердости металла элементов оборудования")
    @PatchMapping
    public ResponseEntity<ResponseAcceptableHardnessDto> update(
            @RequestBody @Parameter(name = "Данные допустимой твердости металла") AcceptableHardnessDto hardnessDto) {
        return ResponseEntity.ok().body(service.update(hardnessDto));
    }

    @Operation(summary = "Получить данные допустимых значений твердости металла элементов оборудования")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseAcceptableHardnessDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор типа оборудования") Long equipmentTypeId) {
        return ResponseEntity.ok().body(service.getAll(equipmentTypeId));
    }

    @Operation(summary = "Удалить данные допустимой твердости металла элементов")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные допустимой твердости металла элементов оборудования успешно удалены.");
    }
}