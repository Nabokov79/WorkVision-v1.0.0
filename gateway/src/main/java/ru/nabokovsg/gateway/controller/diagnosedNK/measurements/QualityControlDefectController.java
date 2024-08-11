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
import ru.nabokovsg.gateway.client.diagnosedNK.measurements.QualityControlDefectClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.qualityControlDefect.NewQualityControlDefectDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/measurement/quality/defect",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные обнаруженных дефектов сварных соединений",
        description="API для работы с данными дефектов сварных соединений")
public class QualityControlDefectController {

    private final QualityControlDefectClient client;

    @Operation(summary = "Добавить данные измеренного дефекта")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(name = "Данные измеренного дефекта")
                                                         NewQualityControlDefectDto defectDto) {
        return client.save(defectDto);
    }

    @Operation(summary = "Получить данные измеренных дефектов элементов(подэлементов) оборудования" +
            " по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор записи рабочего журнала") Long workJournalId) {
        return client.getAll(workJournalId);
    }

    @Operation(summary = "Удалить измеренный дефект")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}