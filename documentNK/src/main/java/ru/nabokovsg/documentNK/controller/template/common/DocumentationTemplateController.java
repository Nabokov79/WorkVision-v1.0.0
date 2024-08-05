package ru.nabokovsg.documentNK.controller.template.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.common.documentationTemplate.DocumentationTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.documentationTemplate.ResponseDocumentationTemplateDto;
import ru.nabokovsg.documentNK.service.template.common.DocumentationTemplateService;

@RestController
@RequestMapping(
        value = "/template/measuring/documentation",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Шаблон нормативно-технической документации",
        description="API для добавления нормативно-технической документации в шаблон документа")
public class DocumentationTemplateController {

    private final DocumentationTemplateService service;

    @Operation(summary = "Добавление данных нормативно-технической документации")
    @PostMapping
    public ResponseEntity<ResponseDocumentationTemplateDto> save(
                                        @RequestBody
                                        @Parameter(description = "Данные шаблона нормативно-технической документации")
                                        DocumentationTemplateDto documentationDto) {
        return ResponseEntity.ok().body(service.save(documentationDto));
    }

    @Operation(summary = "Изменение данных нормативно-технической документации")
    @PatchMapping
    public ResponseEntity<ResponseDocumentationTemplateDto> update(
                                        @RequestBody
                                        @Parameter(description = "Данные шаблона нормативно-технической документации")
                                        DocumentationTemplateDto documentationDto) {
        return ResponseEntity.ok().body(service.update(documentationDto));
    }

    @Operation(summary = "Удалить шаблон средства измерения")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Шаблон средства измерения успешно удален.");
    }
}