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
import ru.nabokovsg.gateway.client.laboratoryNK.DocumentationClient;
import ru.nabokovsg.gateway.dto.laboratoryNK.documentation.NewDocumentationDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.documentation.UpdateDocumentationDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/documentation",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Нормативно-техническая документация",
        description="API для работы с данными нормативно-технической документации")
public class DocumentationController {

    private final DocumentationClient client;

    @Operation(summary = "Добавление данных документа")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                             @Parameter(description = "Документ") NewDocumentationDto documentationDto) {
        return client.save(documentationDto);
    }

    @Operation(summary = "Изменение данных документа")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                               @Parameter(description = "Документ") UpdateDocumentationDto documentationDto) {
        return client.update(documentationDto);
    }

    @Operation(summary = "Получение данных документов")
    @GetMapping
    public Flux<Object> getAll(@RequestParam(name = "number", required = false)
                               @Parameter(description = "Номер документа") String number
                             , @RequestParam(name = "title", required = false)
                               @Parameter(description = "Название документа") String title) {
        return client.getAll(number, title);
    }

    @Operation(summary = "Удаление данных документа")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}