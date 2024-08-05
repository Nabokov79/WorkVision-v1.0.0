package ru.nabokovsg.gateway.controller.diagnosedEquipment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.diagnosedEquipment.EquipmentInspectionClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentInspection.NewEquipmentInspectionDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentInspection.UpdateEquipmentInspectionDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/equipments/inspection",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные выполненных обследований оборудования",
        description="API для работы с данными обследований оборудования")
public class EquipmentInspectionController {

    private final EquipmentInspectionClient client;

    @Operation(summary = "Добавить данные обследования оборудования")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                             @Parameter(name = "Обследование оборудования") NewEquipmentInspectionDto inspectionDto) {
        return client.save(inspectionDto);
    }

    @Operation(summary = "Изменить данные обследования оборудования")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                            @Parameter(name = "Обследование оборудования") UpdateEquipmentInspectionDto inspectionDto) {
        return client.update(inspectionDto);
    }

    @Operation(summary = "Получить данные всех обследований оборудования")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(description = "Индентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }

    @Operation(summary = "Удалить измеренный обследования")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Индентификатор") Long id) {
        return client.delete(id);
    }
}