package ru.nabokovsg.laboratoryNK.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.laboratoryNK.dto.documentation.DocumentationDto;
import ru.nabokovsg.laboratoryNK.service.DocumentationService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/laboratory/nk/documentation",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Нормативно-техническая документация",
        description="API для работы с данными нормативно-технической документации")
public class DocumentationController {

    private final DocumentationService service;

    @Operation(summary = "Добавление данных документа")
    @PostMapping
    public ResponseEntity<DocumentationDto> save(
            @RequestBody @Parameter(description = "Документ") DocumentationDto documentationDto) {
        return ResponseEntity.ok().body(service.save(documentationDto));
    }

    @Operation(summary = "Изменение данных документа")
    @PatchMapping
    public ResponseEntity<DocumentationDto> update(
            @RequestBody @Parameter(description = "Документ") DocumentationDto documentationDto) {
        return ResponseEntity.ok().body(service.update(documentationDto));
    }

    @Operation(summary = "Получить документ для добавления в шаблон документа")
    @GetMapping("/{id}")
    public ResponseEntity<DocumentationDto> get(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получение данных документов")
    @GetMapping("/all")
    public ResponseEntity<List<DocumentationDto>> getAll(
                                                  @RequestParam(name = "number", required = false)
                                                  @Parameter(description = "Номер документа") String number
                                                , @RequestParam(name = "title", required = false)
                                                  @Parameter(description = "Название документа") String title) {
        return ResponseEntity.ok().body(service.getAll(number, title));
    }

    @Operation(summary = "Удаление данных документа")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Документ успешно удален.");
    }
}