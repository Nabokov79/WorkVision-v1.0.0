package ru.nabokovsg.documentNK.controller.template.protocolControl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.protocolControl.informationObjectOfControlTemplate.InformationObjectOfControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.informationObjectOfControlTemplate.ResponseInformationObjectOfControlTemplateDto;
import ru.nabokovsg.documentNK.service.template.protocolControl.InformationAboutObjectOfControlTemplateService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/template/protocol/control/object/information",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Шаблон общих данных контролируемого объекта",
        description="API для работы с данными шаблона общих данных контролируемого объекта")
public class InformationAboutObjectOfControlTemplateController {

    private final InformationAboutObjectOfControlTemplateService service;

    @Operation(summary = "Данные нового шаблона общих данных контролируемого объекта")
    @PostMapping
    public ResponseEntity<ResponseInformationObjectOfControlTemplateDto> save(
            @RequestBody @Parameter(description = "Шаблон общих данных контролируемого объекта")
            InformationObjectOfControlTemplateDto templateDto) {
        return ResponseEntity.ok().body(service.save(templateDto));
    }

    @Operation(summary = "Данные для изменения общих данных контролируемого объекта")
    @PatchMapping
    public ResponseEntity<ResponseInformationObjectOfControlTemplateDto> update(
            @RequestBody @Parameter(description = "Шаблон общих данных контролируемого объекта")
                    InformationObjectOfControlTemplateDto templateDto) {
        return ResponseEntity.ok().body(service.update(templateDto));
    }

    @Operation(summary = "Получить данные шаблона общих данных контролируемого объекта")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseInformationObjectOfControlTemplateDto> get(
            @PathVariable @Parameter(name = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить шаблоны общих данных контролируемого объекта")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseInformationObjectOfControlTemplateDto>> getAll(
            @PathVariable(name = "id") @Parameter(description = "Идентификатор типа документа") Long documentTypeId) {
        return ResponseEntity.ok().body(service.getAll(documentTypeId));
    }

    @Operation(summary = "Удаление шаблон общих данных контролируемого объекта")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Шаблон общих данных контролируемого объекта удален.");
    }
}