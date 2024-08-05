package ru.nabokovsg.documentNK.controller.template.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.common.measuringToolTemplate.MeasuringToolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.measuringToolTemplate.ResponseMeasuringToolTemplateDto;
import ru.nabokovsg.documentNK.service.template.common.MeasuringToolTemplateService;

@RestController
@RequestMapping(
        value = "/template/measuring/tool",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Шаблон средства, прибора измерения",
        description="API для работы с данными шаблона средства, прибора измерения")
public class MeasuringToolTemplateController {

    private final MeasuringToolTemplateService service;

    @Operation(summary = "Добавление данных средств или приборов измерения")
    @PostMapping
    public ResponseEntity<ResponseMeasuringToolTemplateDto> save(
            @RequestBody
            @Parameter(description = "Средство или приборы измерения") MeasuringToolTemplateDto measuringToolDto) {
        return ResponseEntity.ok().body(service.save(measuringToolDto));
    }

    @Operation(summary = "Изменение данных средства или прибора измерения")
    @PatchMapping
    public ResponseEntity<ResponseMeasuringToolTemplateDto> update(
            @RequestBody
            @Parameter(description = "Средство или приборы измерения") MeasuringToolTemplateDto measuringToolDto) {
        return ResponseEntity.ok().body(service.update(measuringToolDto));
    }

    @Operation(summary = "Удалить шаблон средства измерения")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Шаблон средства измерения успешно удален.");
    }
}