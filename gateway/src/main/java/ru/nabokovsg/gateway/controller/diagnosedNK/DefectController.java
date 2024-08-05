package ru.nabokovsg.gateway.controller.diagnosedNK;

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
import ru.nabokovsg.gateway.client.diagnosedNK.DefectClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.defectsNorm.NewDefectDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.defectsNorm.UpdateDefectDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/norms/defects",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Дефекты элемента оборудования",
        description="API для работы с дефектами элементов оборудования")
public class DefectController {

    private final DefectClient client;

    @Operation(summary = "Добавление новых дефектов оборудования")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(description = "Дефект") NewDefectDto defectDto) {
        return client.save(defectDto);
    }

    @Operation(summary = "Изменение данных дефектов оборудования")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid @Parameter(description = "Дефект") UpdateDefectDto defectDto) {
        return client.update(defectDto);
    }

    @Operation(summary = "Получить дефект")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получить дефекты")
    @GetMapping
    public Flux<Object> getAll() {
        return client.getAll();
    }

    @Operation(summary = "Удалить дефект")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return  client.delete(id);
    }
}