package ru.nabokovsg.gateway.controller.diagnosedNK.equipment;

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
import ru.nabokovsg.gateway.client.diagnosedNK.equipment.EquipmentDiagnosedClient;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentDiagnosed.NewEquipmentDto;
import ru.nabokovsg.gateway.dto.diagnosedEquipment.diagnosedEquipment.equipmentDiagnosed.UpdateEquipmentDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/equipments",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Оборудование",
        description="API для работы с данными оборудования котельных, ЦТП")
public class EquipmentDiagnosedController {

    private final EquipmentDiagnosedClient client;

    @Operation(summary = "Добавление данных оборудования")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                             @Parameter(description = "Оборудование") NewEquipmentDto equipmentDto) {
        return client.save(equipmentDto);
    }

    @Operation(summary = "Изменение данных оборудования")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                               @Parameter(description = "Оборудование") UpdateEquipmentDto equipmentDto) {
        return client.update(equipmentDto);
    }

    @Operation(summary = "Получить оборудование")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получить все оборудование котельной, ЦТП")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(description = "Идентификатор котельной, ЦТП") Long buildingId) {
        return client.getAll(buildingId);
    }

    @Operation(summary = "Удаление оборудования")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}