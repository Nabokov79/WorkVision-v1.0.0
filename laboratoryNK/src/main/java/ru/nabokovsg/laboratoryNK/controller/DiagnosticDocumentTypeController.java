package ru.nabokovsg.laboratoryNK.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.laboratoryNK.dto.diagnosticDocument.DiagnosticDocumentTypeDto;
import ru.nabokovsg.laboratoryNK.service.DiagnosticDocumentTypeService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/laboratory/nk/document/type",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Тип документа по результатам диагностики",
        description="API для работы с данными вида документа по результатам диагностики")
public class DiagnosticDocumentTypeController {

    private final DiagnosticDocumentTypeService service;

    @Operation(summary = "Добавление нового типа документа")
    @PostMapping
    public ResponseEntity<DiagnosticDocumentTypeDto> save(
                     @RequestBody @Parameter(description = "Тип документа") DiagnosticDocumentTypeDto documentTypeDto) {
        return ResponseEntity.ok().body(service.save(documentTypeDto));
    }

    @Operation(summary = "Изменение информации в шаблоне ")
    @PatchMapping
    public ResponseEntity<DiagnosticDocumentTypeDto> update(
                     @RequestBody @Parameter(description = "Тип документа") DiagnosticDocumentTypeDto documentTypeDto) {
        return ResponseEntity.ok().body(service.update(documentTypeDto));
    }

    @Operation(summary = "Получить тип документа")
    @GetMapping("/{id}")
    public ResponseEntity<DiagnosticDocumentTypeDto> get(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить все типы документов")
    @GetMapping("/all")
    public ResponseEntity<List<DiagnosticDocumentTypeDto>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Удалить типа документа")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Тип документа успешно удален.");
    }
}