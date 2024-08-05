package ru.nabokovsg.documentNK.controller.template.reportSurvey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle.PageTitleTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.pageTitle.ResponsePageTitleTemplateDto;
import ru.nabokovsg.documentNK.service.template.reportSurvey.PageTitleTemplateService;

@RestController
@RequestMapping(
        value = "/template/title-page",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Шаблон титульного листа",
        description="API для работы с данными шаблона титульного листа")
public class PageTitleTemplateController {

    private final PageTitleTemplateService service;

    @Operation(summary = "Добавление шаблона титульного листа")
    @PostMapping
    public ResponseEntity<ResponsePageTitleTemplateDto> save(
           @RequestBody @Parameter(description = "Данные шаблона титульного листа") PageTitleTemplateDto pageTitleDto) {
        return ResponseEntity.ok().body(service.save(pageTitleDto));
    }

    @Operation(summary = "Изменение шаблона титульного листа")
    @PatchMapping
    public ResponseEntity<ResponsePageTitleTemplateDto> update(
           @RequestBody @Parameter(description = "Данные шаблона титульного листа") PageTitleTemplateDto pageTitleDto) {
        return ResponseEntity.ok().body(service.update(pageTitleDto));
    }

    @Operation(summary = "Получить шаблон титульного листа")
    @GetMapping("/{id}")
    public ResponseEntity<ResponsePageTitleTemplateDto> get(
            @PathVariable @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Удалить шаблон титульного листа")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Шаблон титульного листа успешно удален.");
    }
}