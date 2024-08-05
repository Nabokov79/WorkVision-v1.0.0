package ru.nabokovsg.documentNK.controller.template.protocolControl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ResponseProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.dto.template.protocolControl.ShortResponseProtocolControlTemplateDto;
import ru.nabokovsg.documentNK.service.template.protocolControl.ProtocolControlTemplateService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/template/protocol/control",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Шаблон протокола по контролю качества",
        description="API для работы с данными шаблона протокола по контролю качества")
public class ProtocolControlTemplateController {

    private final ProtocolControlTemplateService service;

    @Operation(summary = "Данные нового шаблона протокола по контролю качества")
    @PostMapping
    public ResponseEntity<ShortResponseProtocolControlTemplateDto> save(
                                            @RequestBody
                                            @Parameter(description = "Шаблон протокола по контролю качества")
                                            ProtocolControlTemplateDto protocolDto) {
        return ResponseEntity.ok().body(service.save(protocolDto));
    }

    @Operation(summary = "Данные для изменения информации в шаблоне протокола по контролю качества")
    @PatchMapping
    public ResponseEntity<ShortResponseProtocolControlTemplateDto> update(
                                            @RequestBody
                                            @Parameter(description = "Шаблон протокола по контролю качества")
                                            ProtocolControlTemplateDto protocolDto) {
        return ResponseEntity.ok().body(service.update(protocolDto));
    }

    @Operation(summary = "Получить данные шаблона протокола/заключения по контролю качества")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProtocolControlTemplateDto> get(
                                                            @PathVariable @Parameter(name = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить краткие данные шаблонов протоколов по контролю качества")
    @GetMapping("/all")
    public ResponseEntity<List<ShortResponseProtocolControlTemplateDto>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Удаление шаблона протокола/заключения по контролю качества")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Шаблон протокола по контролю качества удалены.");
    }
}
