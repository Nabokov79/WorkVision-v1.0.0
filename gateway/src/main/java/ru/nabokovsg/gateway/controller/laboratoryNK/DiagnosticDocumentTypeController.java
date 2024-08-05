package ru.nabokovsg.gateway.controller.laboratoryNK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.laboratoryNK.DiagnosticDocumentTypeClient;
import ru.nabokovsg.gateway.dto.laboratoryNK.diagnosticDocumentsType.NewDiagnosticDocumentTypeDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.diagnosticDocumentsType.UpdateDiagnosticDocumentTypeDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/document/type",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Тип документа по результатам диагностики",
        description="API для работы с данными вида документа по результатам диагностики")
public class DiagnosticDocumentTypeController {

    private final DiagnosticDocumentTypeClient client;

    @Operation(summary = "Добавление нового типа документа")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                             @Parameter(description = "Тип документа") NewDiagnosticDocumentTypeDto documentTypeDto) {
        return client.save(documentTypeDto);
    }

    @Operation(summary = "Изменение информации в шаблоне ")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                            @Parameter(description = "Тип документа") UpdateDiagnosticDocumentTypeDto documentTypeDto) {
        return client.update(documentTypeDto);
    }

    @Operation(summary = "Получить тип документа")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получить все типы документов")
    @GetMapping("/all")
    public Flux<Object> getAll() {
        return client.getAll();
    }

    @Operation(summary = "Удалить типа документа")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}