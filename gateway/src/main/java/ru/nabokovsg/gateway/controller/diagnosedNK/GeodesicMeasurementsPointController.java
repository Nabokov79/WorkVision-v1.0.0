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
import ru.nabokovsg.gateway.client.diagnosedNK.GeodesicMeasurementsPointClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.geodesy.NewGeodesicMeasurementsPointDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/measurement/survey/geodesy",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные измерений при выполнении геодезической съемки(нивелировании) оборудования",
        description="API для работы с данными измерений при выполнении геодезической съемки(нивелировании) оборудования")
public class GeodesicMeasurementsPointController {

    private final GeodesicMeasurementsPointClient client;

    @Operation(summary = "Добавить данные геодезический съемки оборудования")
    @PostMapping
    public Flux<Object> save(@RequestBody @Valid @Parameter(name = "Данные измерений геодезической съемки")
                                                            NewGeodesicMeasurementsPointDto measurementDto) {
        return client.save(measurementDto);
    }

    @Operation(summary = "Получить данные геодезических измерений по идентификатору записи журнала задач")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }

    @Operation(summary = "Удалить результаты геодезической съемки")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}