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
import ru.nabokovsg.gateway.client.documentNK.InformationAboutObjectOfControlClient;
import ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControl.NewInformationAboutObjectOfControlDto;
import ru.nabokovsg.gateway.dto.documentNK.informationAboutObjectOfControl.UpdateInformationAboutObjectOfControlDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/document",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Сведения об объекте контроля",
        description="API для работы с данными сведений об объекте контроля")
public class InformationAboutObjectOfControlController {

    private final InformationAboutObjectOfControlClient client;

    @Operation(summary = "Новые сведения об объекте контроля")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(description = "Сведения об объекте контроля")
                                                          NewInformationAboutObjectOfControlDto informationDto) {
        return client.save(informationDto);
    }

    @Operation(summary = "Изменения сведений об объекте контроля")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid @Parameter(description = "Сведения об объекте контроля")
                                                              UpdateInformationAboutObjectOfControlDto informationDto) {
        return client.update(informationDto);
    }

    @Operation(summary = "Получить сведения об объекте контроля")
    @GetMapping("/all{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                                                @Parameter(description = "Идентификатор протокола") Long protocolId) {
        return client.getAll(protocolId);
    }

    @Operation(summary = "Удаление сведения об объекте контроля")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}