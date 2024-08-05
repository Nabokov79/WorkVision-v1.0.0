package ru.nabokovsg.documentNK.controller.template.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.common.documentHeader.DocumentHeaderTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.documentHeader.ResponseDocumentHeaderTemplateDto;
import ru.nabokovsg.documentNK.service.template.common.DocumentHeaderTemplateService;

import java.util.List;
@RestController
@RequestMapping(
        value = "/template/document/header",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Заголовк документа",
        description="API для работы с данными заголовка протоколов и титульных листов отчетов")
public class DocumentHeaderController {

    private final DocumentHeaderTemplateService service;

    @Operation(summary = "Добавление нового заголовка")
    @PostMapping
    public ResponseEntity<ResponseDocumentHeaderTemplateDto> save(
            @RequestBody @Parameter(description = "Данные формирования заголовка") DocumentHeaderTemplateDto headerDto) {
        return ResponseEntity.ok().body(service.save(headerDto));
    }

    @Operation(summary = "Изменение информации в заголовка")
    @PatchMapping
    public ResponseEntity<ResponseDocumentHeaderTemplateDto> update(
            @RequestBody @Parameter(description = "Данные формирования заголовка") DocumentHeaderTemplateDto headerDto) {
        return ResponseEntity.ok().body(service.update(headerDto));
    }

    @Operation(summary = "Получить заголовки документа")
    @GetMapping("/{id}")
    public ResponseEntity<List<ResponseDocumentHeaderTemplateDto>> getAll(
            @PathVariable @Parameter(name = "Идентификатор типа документа") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удалить заголовок")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Заголовок успешно удален.");
    }
}