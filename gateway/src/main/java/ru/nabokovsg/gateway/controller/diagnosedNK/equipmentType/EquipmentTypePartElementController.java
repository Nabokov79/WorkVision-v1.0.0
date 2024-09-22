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
import ru.nabokovsg.gateway.client.diagnosedNK.equipmentType.EquipmentTypePartElementClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentTypePartElement.NewEquipmentTypePartElementDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.equipmentType.equipmentTypePartElement.UpdateEquipmentTypePartElementDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/equipment/type/element/part",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Подэлемент",
        description="API для работы с данными подэлементов элементов")
public class EquipmentTypePartElementController {

    private final EquipmentTypePartElementClient client;

    @Operation(summary = "Добавление нового подэлемента")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                             @Parameter(description = "Подэлемент") NewEquipmentTypePartElementDto partElementDto) {
        return client.save(partElementDto);
    }

    @Operation(summary = "Изменение данных подэлемента")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                              @Parameter(description = "Подэлемент") UpdateEquipmentTypePartElementDto partElementDto) {
        return client.update(partElementDto);
    }

    @Operation(summary = "Получить все подэлементы элемента")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                                @Parameter(description = "Идентификатор элемента") Long elementId) {
        return client.getAll(elementId);
    }
}