package ru.nabokovsg.gateway.controller.laboratoryNK;

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
import ru.nabokovsg.gateway.client.laboratoryNK.MeasuringToolClient;
import ru.nabokovsg.gateway.dto.laboratoryNK.measuringTool.NewMeasuringToolDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.measuringTool.SearchParameters;
import ru.nabokovsg.gateway.dto.laboratoryNK.measuringTool.UpdateMeasuringToolDto;
import java.time.LocalDate;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/toll",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Средства и приборы измерения и контроля",
     description="API для работы с средств и приборов измерения и контроля")
public class MeasuringToolController {

    private final MeasuringToolClient client;

    @Operation(summary = "Добавление данных средств или приборов измерения")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                       @Parameter(description = "Средство или прибор измерения") NewMeasuringToolDto measuringToolDto) {
        return client.save(measuringToolDto);
    }

    @Operation(summary = "Изменение данных средства или прибора измерения")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                    @Parameter(description = "Средство или прибор измерения") UpdateMeasuringToolDto measuringToolDto) {
        return client.update(measuringToolDto);
    }

    @Operation(summary = "Получение средств или приборов измерения")
    @GetMapping
    public Flux<Object> getAll(
            @RequestParam(required = false) @Parameter(description = "Название") String toll,
            @RequestParam(required = false) @Parameter(description = "Модель") String model,
            @RequestParam(required = false) @Parameter(description = "Заводской номер") String workNumber,
            @RequestParam(required = false) @Parameter(description = "Дата изготовления") LocalDate manufacturing,
            @RequestParam(required = false) @Parameter(description = "Дата начала эксплуатации") LocalDate exploitation,
            @RequestParam(required = false) @Parameter(description = "Завод-изготовитель") String manufacturer,
            @RequestParam(required = false) @Parameter(description = "Метрологическая организация") String organization,
            @RequestParam(required = false) @Parameter(description = "Вид контроля") String controlType,
            @RequestParam(required = false) @Parameter(description = "Идентификатор сотрудника") Long employeeId) {
        return client.getAll(new SearchParameters(toll, model, workNumber, manufacturing,
                                                  exploitation, manufacturer, organization,
                                                  controlType, employeeId));
    }

    @Operation(summary = "Удаление средства или приборы измерения")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}