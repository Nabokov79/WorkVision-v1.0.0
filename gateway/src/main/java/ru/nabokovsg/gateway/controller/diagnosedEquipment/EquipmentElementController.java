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
import ru.nabokovsg.gateway.client.diagnosedEquipment.EquipmentElementClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentElement.NewEquipmentElementDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentElement.UpdateEquipmentElementDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/equipments/element",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Элемент",
        description="API для работы с данными элементов оборудования")
public class EquipmentElementController {

    private final EquipmentElementClient client;

    @Operation(summary = "Добавление нового элемента")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                             @Parameter(description = "Элемент") NewEquipmentElementDto elementDto) {
        return client.save(elementDto);
    }

    @Operation(summary = "Изменение данных элемента")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                               @Parameter(description = "Элемент") UpdateEquipmentElementDto elementDto) {
        return client.update(elementDto);
    }

    @Operation(summary = "Получить все элементы диагностируемого оборудования")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(description = "Индентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }
}
