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
import ru.nabokovsg.gateway.client.laboratoryNK.LaboratoryCertificateClient;
import ru.nabokovsg.gateway.dto.laboratoryNK.laboratoryCertificate.NewLaboratoryCertificateDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.laboratoryCertificate.UpdateLaboratoryCertificateDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/laboratory/certificate",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Аттестация лаборатории",
        description="API для работы с данными аттестации лаборатории НК")
public class LaboratoryCertificateController {

    private final LaboratoryCertificateClient client;

    @Operation(summary = "Добавление сведений об аттестации")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                        @Parameter(description = "Сведения об аттестации") NewLaboratoryCertificateDto certificateDto) {
        return client.save(certificateDto);
    }

    @Operation(summary = "Изменение сведений об аттестации")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                     @Parameter(description = "Сведения об аттестации") UpdateLaboratoryCertificateDto certificateDto) {
        return client.update(certificateDto);
    }

    @Operation(summary = "Получение всех сохраненных аттестаций")
    @GetMapping
    public Flux<Object> getAll() {
        return client.getAll();
    }

    @Operation(summary = "Удаление сведений об аттестации")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}