package ru.nabokovsg.documentNK.controller.template.reportSurvey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.ResponseSectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.SectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.section.ShortResponseSectionTemplateDto;
import ru.nabokovsg.documentNK.service.template.reportSurvey.SectionTemplateService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/template/section",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Шаблон раздела отчета",
        description="API для работы с данными шаблона раздела")
public class SectionTemplateController {

    private final SectionTemplateService service;

    @Operation(summary = "Добавить новый раздел")
    @PostMapping
    public ResponseEntity<ResponseSectionTemplateDto> save(
            @RequestBody @Parameter(description = "Данные шаблона раздела") SectionTemplateDto sectionDto) {
        return ResponseEntity.ok().body(service.save(sectionDto));
    }

    @Operation(summary = "Изменение данные раздела")
    @PatchMapping
    public ResponseEntity<ResponseSectionTemplateDto> update(
            @RequestBody @Parameter(description = "Данные шаблона раздела") SectionTemplateDto sectionDto) {
        return ResponseEntity.ok().body(service.update(sectionDto));
    }

    @Operation(summary = "Получить раздел")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSectionTemplateDto> get(@PathVariable
                                                          @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить разделы отчета")
    @GetMapping("/all/report/{id}")
    public ResponseEntity<List<ShortResponseSectionTemplateDto>> getAll(
                                      @PathVariable @Parameter(description = "Идентификатор шаблона отчета") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удалить раздел")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Шаблон раздела успешно удален.");
    }
}