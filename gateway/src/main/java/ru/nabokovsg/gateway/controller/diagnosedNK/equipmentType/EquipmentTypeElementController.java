package ru.nabokovsg.gateway.controller.diagnosedNK.equipmentType;

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
import ru.nabokovsg.gateway.client.diagnosedNK.equipmentType.EquipmentTypeElementClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentTypeElement.NewEquipmentTypeElementDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentTypeElement.UpdateEquipmentTypeElementDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/equipments/type/element",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Элемент",
        description="API для работы с данными элементов оборудования")
public class EquipmentTypeElementController {

    private final EquipmentTypeElementClient client;

    @Operation(summary = "Добавление нового элемента")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                             @Parameter(description = "Элемент") NewEquipmentTypeElementDto elementDto) {
        return client.save(elementDto);
    }

    @Operation(summary = "Изменение данных элемента")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                               @Parameter(description = "Элемент") UpdateEquipmentTypeElementDto elementDto) {
        return client.update(elementDto);
    }

    @Operation(summary = "Получить все элементы оборудования")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(description = "Идентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }
}
