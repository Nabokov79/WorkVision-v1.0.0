package ru.nabokovsg.documentNK.controller.template.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.common.conclusion.ConclusionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.conclusion.ResponseConclusionTemplateDto;
import ru.nabokovsg.documentNK.service.template.common.ConclusionTemplateService;

@RestController
@RequestMapping(
        value = "/template/conclusion",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Шаблоны заключений к документам",
        description="API для работы с шаблонами заключений к документам")
public class ConclusionTemplateController {

    private final ConclusionTemplateService service;

    @Operation(summary = "Добавление нового шаблона заключения")
    @PostMapping
    public ResponseEntity<ResponseConclusionTemplateDto> save(
            @RequestBody @Parameter(description = "Шаблон заключения") ConclusionTemplateDto conclusionDto) {
        return ResponseEntity.ok().body(service.save(conclusionDto));
    }

    @Operation(summary = "Изменение шаблона заключения")
    @PatchMapping
    public ResponseEntity<ResponseConclusionTemplateDto> update(
                       @RequestBody @Parameter(description = "Шаблон заключения") ConclusionTemplateDto conclusionDto) {
        return ResponseEntity.ok().body(service.update(conclusionDto));
    }

    @Operation(summary = "Получить шаблон заключения")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseConclusionTemplateDto> get(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Удалить шаблона заключения")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Шаблон заключения успешно удален.");
    }
}