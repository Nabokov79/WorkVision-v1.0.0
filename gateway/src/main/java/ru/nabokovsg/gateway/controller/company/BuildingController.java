package ru.nabokovsg.gateway.controller.company;

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
import ru.nabokovsg.gateway.client.company.BuildingClient;
import ru.nabokovsg.gateway.dto.company.building.NewBuildingDto;
import ru.nabokovsg.gateway.dto.company.building.UpdateBuildingDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/building",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Котельная(ЦТП)",
        description="API для работы с информацией о котельной(цтп)")
public class BuildingController {

    private final BuildingClient client;

    @Operation(summary = "Добавление новой информации о котельной(цтп)")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                                     @Parameter(description = "Котельная, ЦТП") NewBuildingDto buildingDto) {
        return client.save(buildingDto);
    }

    @Operation(summary = "Изменение данных котельной(цтп)")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                                   @Parameter(description = "Котельная, ЦТП") UpdateBuildingDto buildingDto) {
        return client.update(buildingDto);
    }

    @Operation(summary = "Получение данных всех котельных(цтп)")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получение данных всех котельных(цтп)")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable @NotNull @Positive
                                        @Parameter(description = "Идентификатор эксплуатационного участка") Long id) {
        return client.getAll(id);
    }

    @Operation(summary = "Удаление данных котельной(цтп)")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}