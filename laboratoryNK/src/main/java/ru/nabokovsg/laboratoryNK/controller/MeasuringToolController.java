package ru.nabokovsg.laboratoryNK.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.MeasuringToolDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.ResponseMeasuringToolDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.SearchParameters;
import ru.nabokovsg.laboratoryNK.service.MeasuringToolService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(
        value = "/laboratory/nk/instrument",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Средства и приборы измерения",
     description="API для работы с средств и приборов измерения")
public class MeasuringToolController {

    private final MeasuringToolService service;

    @Operation(summary = "Добавление данных средств или прибора измерения")
    @PostMapping
    public ResponseEntity<ResponseMeasuringToolDto> save(
                         @RequestBody
                         @Parameter(description = "Средство или прибор измерения") MeasuringToolDto measuringToolDto) {
        return ResponseEntity.ok().body(service.save(measuringToolDto));
    }

    @Operation(summary = "Изменение данных средства или прибора измерения")
    @PatchMapping
    public ResponseEntity<ResponseMeasuringToolDto> update(
                         @RequestBody
                         @Parameter(description = "Средство или прибор измерения") MeasuringToolDto measuringToolDto) {
        return ResponseEntity.ok().body(service.update(measuringToolDto));
    }

    @Operation(summary = "Получить средство или прибор измерения для добавления к шаблону документа")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMeasuringToolDto> get(@PathVariable
                                                        @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получение средств или приборов измерения")
    @GetMapping("/all")
    public ResponseEntity<List<ResponseMeasuringToolDto>> getAll(
     @RequestParam(name = "employeeId", required = false)
     @Parameter(description = "Идентификатры сотрудников") List<Long> ids,
     @RequestParam(required = false) @Parameter(description = "Название") String toll,
     @RequestParam(required = false) @Parameter(description = "Модель") String model,
     @RequestParam(required = false) @Parameter(description = "Заводской номер") String workNumber,
     @RequestParam(required = false) @Parameter(description = "Дата изготовления") LocalDate manufacturing,
     @RequestParam(required = false) @Parameter(description = "Дата начала эксплуатации") LocalDate exploitation,
     @RequestParam(required = false) @Parameter(description = "Завод-изготовитель") String manufacturer,
     @RequestParam(required = false) @Parameter(description = "Метрологическая организация") String organization,
     @RequestParam(required = false) @Parameter(description = "Вид контроля") String controlType,
     @RequestParam(required = false) @Parameter(description = "Идентификатор сотрудника") Long employeeId) {
        return ResponseEntity.ok().body(service.getAll(new SearchParameters(ids, toll, model, workNumber, manufacturing,
                                                                               exploitation, manufacturer, organization,
                                                                               controlType, employeeId)));
    }

    @Operation(summary = "Удаление средства или приборы измерения")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Средство или прибор измерения успешно удален.");
    }
}