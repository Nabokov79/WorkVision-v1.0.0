package ru.nabokovsg.gateway.controller.diagnosedNK.measurements;

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
import ru.nabokovsg.gateway.client.diagnosedNK.measurements.IdentifiedDefectClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.identifiedDefect.NewIdentifiedDefectDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.identifiedDefect.UpdateIdentifiedDefectDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/measurement/survey/defect",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные обнаруженных дефектов элементов, подэлементов оборудования",
        description="API для работы с данными дефектов элементов, подэлементов оборудования")
public class IdentifiedDefectController {

    private final IdentifiedDefectClient client;

    @Operation(summary = "Добавить данные измеренного дефекта")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(name = "Данные измеренного дефекта")
                                                               NewIdentifiedDefectDto defectDto) {
        return client.save(defectDto);
    }

    @Operation(summary = "Изменить данные измеренного дефекта")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid @Parameter(name = "Данные измеренного дефекта")
                                                            UpdateIdentifiedDefectDto defectDto) {
        return client.update(defectDto);
    }

    @Operation(summary = "Получить данные измеренных дефектов элементов(подэлементов) оборудования" +
                        " по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }

    @Operation(summary = "Удалить измеренный дефект")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}