package ru.nabokovsg.gateway.controller.diagnosedNK.measurements;

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
import ru.nabokovsg.gateway.client.diagnosedNK.measurements.UltrasonicControlClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.ultrasonicControl.NewUltrasonicMeasurementDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.ultrasonicControl.UpdateUltrasonicMeasurementDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/measurement/ultrasonic",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные ультразвукового контроля сварного соединения",
        description="API для работы с данными ультразвукового контроля сварного соединения")
public class UltrasonicControlController {

    private final UltrasonicControlClient client;

    @Operation(summary = "Добавить данные ультразвукового контроля")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(name = "Данные ультразвукового контроля")
                                                          NewUltrasonicMeasurementDto measurementDto) {
        return client.save(measurementDto);
    }

    @Operation(summary = "Добавить данные измеренного дефекта")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid @Parameter(name = "Данные ультразвукового контроля")
                                                         UpdateUltrasonicMeasurementDto measurementDto) {
        return client.update(measurementDto);
    }

    @Operation(summary = "Получить данные ультразвукового контроля по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор записи рабочего журнала") Long workJournalId) {
        return client.getAll(workJournalId);
    }

    @Operation(summary = "Удалить данные ультразвукового контроля")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}