package ru.nabokovsg.gateway.controller.documentNK;

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
import ru.nabokovsg.gateway.client.documentNK.InformationAboutObjectOfControlTemplateClient;
import ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControlTemplate.NewInformationObjectOfControlTemplateDto;
import ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControlTemplate.UpdateInformationObjectOfControlTemplateDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/document/template",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Шаблон общих данных контролируемого объекта",
        description="API для работы с данными шаблона общих данных контролируемого объекта")
public class InformationAboutObjectOfControlTemplateController {

    private final InformationAboutObjectOfControlTemplateClient client;

    @Operation(summary = "Данные нового шаблона общих данных контролируемого объекта")
    @PostMapping
    public Mono<Object> save(
                        @RequestBody @Valid @Parameter(description = "Шаблон общих данных контролируемого объекта")
                                                              NewInformationObjectOfControlTemplateDto templateDto) {
        return client.save(templateDto);
    }

    @Operation(summary = "Данные для изменения общих данных контролируемого объекта")
    @PatchMapping
    public Mono<Object> update(
                        @RequestBody @Valid @Parameter(description = "Шаблон общих данных контролируемого объекта")
                                                            UpdateInformationObjectOfControlTemplateDto templateDto) {
        return client.update(templateDto);
    }

    @Operation(summary = "Получить данные шаблона общих данных контролируемого объекта")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получить шаблоны общих данных контролируемого объекта")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                                   @Parameter(description = "Идентификатор типа документа") Long documentTypeId) {
        return client.getAll(documentTypeId);
    }

    @Operation(summary = "Удаление шаблон общих данных контролируемого объекта")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}