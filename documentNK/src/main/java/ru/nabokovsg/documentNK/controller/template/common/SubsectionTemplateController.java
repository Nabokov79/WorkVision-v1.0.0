package ru.nabokovsg.documentNK.controller.template.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.ResponseSubsectionTemplateDto;
import ru.nabokovsg.documentNK.dto.template.common.subsectionTemplate.SubsectionTemplateDto;
import ru.nabokovsg.documentNK.service.template.common.SubsectionTemplateService;

@RestController
@RequestMapping(
        value = "/template/subsection",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Шаблон подраздела",
        description="API для работы с данными шаблона подраздела раздела")
public class SubsectionTemplateController {

    private final SubsectionTemplateService service;

    @Operation(summary = "Добавление нового шаблона подраздела раздела")
    @PostMapping
    public ResponseEntity<ResponseSubsectionTemplateDto> save(
            @RequestBody @Parameter(description = "Шаблон подраздела") SubsectionTemplateDto subsectionsDto) {
        return ResponseEntity.ok().body(service.save(subsectionsDto));
    }

    @Operation(summary = "Изменение данных шаблона подраздела")
    @PatchMapping
    public ResponseEntity<ResponseSubsectionTemplateDto> update(
            @RequestBody @Parameter(description = "Шаблон подраздела") SubsectionTemplateDto subsectionsDto) {
        return ResponseEntity.ok().body(service.update(subsectionsDto));
    }

    @Operation(summary = "Получить данные шаблона подраздела")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSubsectionTemplateDto> get(@PathVariable
                                                             @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Удалить подраздел")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Шаблон подраздела успешно удален.");
    }
}
