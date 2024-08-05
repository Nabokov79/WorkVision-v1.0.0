package ru.nabokovsg.gateway.controller.diagnosedNK;

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
import ru.nabokovsg.gateway.client.diagnosedNK.AcceptableThicknessClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.acceptableThickness.NewAcceptableThicknessDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.acceptableThickness.UpdateAcceptableThicknessDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/norms/thickness",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные минимальных допустимых толщин стенок элементов оборудования",
        description="API для работы с данными норм минимальных допустимых стенок элементов оборудования")
public class AcceptableThicknessController {

    private final AcceptableThicknessClient client;

    @Operation(summary = "Добавить новые данные допустимых толщины стенки")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                             @Parameter(name = "Данные допустимой толщины стенки")
                                           NewAcceptableThicknessDto thicknessDto) {
        return client.save(thicknessDto);
    }

    @Operation(summary = "Изменение данных допустимых толщины стенки")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                                   @Parameter(name = "Данные допустимой толщины стенки")
                                              UpdateAcceptableThicknessDto thicknessDto) {
        return client.update(thicknessDto);
    }

    @Operation(summary = "Получить данные допустимых толщин толщины стенки элементов оборудования")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор типа оборудования") Long equipmentTypeId) {
        return client.getAll(equipmentTypeId);
    }

    @Operation(summary = "Удалить данные допустимых толщины стенки")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}