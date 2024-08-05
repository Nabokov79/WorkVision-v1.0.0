package ru.nabokovsg.documentNK.controller.template.protocolSurvey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.ResponseSurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.ShortResponseSurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolSurvey.SurveyProtocolTemplateDto;
import ru.nabokovsg.documentNK.service.template.protocolSurvey.SurveyProtocolTemplateService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/template/protocol/survey",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Шаблон протокола обследования",
        description="API для работы с данными шаблона протокола обследования")
public class SurveyProtocolTemplateController {

    private final SurveyProtocolTemplateService service;

    @Operation(summary = "Данные нового шаблона протокола обследования")
    @PostMapping
    public ResponseEntity<ShortResponseSurveyProtocolTemplateDto> save(
            @RequestBody
            @Parameter(description = "Шаблон протокола обследования") SurveyProtocolTemplateDto protocolDto) {
        return ResponseEntity.ok().body(service.save(protocolDto));
    }

    @Operation(summary = "Данные для изменения информации в шаблоне протокола обследования")
    @PatchMapping
    public ResponseEntity<ShortResponseSurveyProtocolTemplateDto> update(
            @RequestBody
            @Parameter(description = "Шаблон протокола обследования") SurveyProtocolTemplateDto protocolDto) {
        return ResponseEntity.ok().body(service.update(protocolDto));
    }

    @Operation(summary = "Получить данные шаблона протокола обследования")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseSurveyProtocolTemplateDto> get(@PathVariable
                                                                     @Parameter(name = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить краткие данные шаблонов протоколов обследования")
    @GetMapping("/all")
    public ResponseEntity<List<ShortResponseSurveyProtocolTemplateDto>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Удаление шаблона протокола обследования")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Шаблон протокола обследования удалены.");
    }
}