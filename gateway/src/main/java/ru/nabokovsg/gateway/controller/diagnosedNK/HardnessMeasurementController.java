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
import ru.nabokovsg.gateway.client.diagnosedNK.HardnessMeasurementClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.hardnessMeasurement.NewHardnessMeasurementDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/measurement/survey/hardness",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные измерений твердости металла оборудования",
        description="API для работы с данными измерений твердости металла оборудования")
public class HardnessMeasurementController {

    private final HardnessMeasurementClient client;

    @Operation(summary = "Добавить данные измерений твердости металла элементов, подэлементов оборудования")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid  @Parameter(name = "Данные измерений твердости металла")
                                                                NewHardnessMeasurementDto measurementDto) {
        return client.save(measurementDto);
    }

    @Operation(summary = "Получить данные данные измерений твердости металла элементов, подэлементов оборудования" +
                                                                          " по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор записи в журнале задач") Long equipmentId) {
        return client.getAll(equipmentId);
    }

    @Operation(summary = "Удалить результат измерения твердости")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}