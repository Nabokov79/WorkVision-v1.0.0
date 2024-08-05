package ru.nabokovsg.gateway.controller.laboratoryNK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.laboratoryNK.DiagnosticDocumentClient;

import java.time.LocalDate;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/document",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Документы лаборатории НК по результатам обследования",
        description="API для работы с документами лаборатории НК по результатам обследования")
public class DiagnosticDocumentController {

    private final DiagnosticDocumentClient client;

    @Operation(summary = "Получение данных задачи на выполнение работы")
    @GetMapping
    public Flux<Object> getAll(@RequestParam(value = "startPeriod", required = false)
                               @Parameter(description = "Начало периода") LocalDate startPeriod
                             , @RequestParam(value = "endPeriod", required = false)
                               @Parameter(description = "Окончание периода") LocalDate endPeriod
                             , @RequestParam(value = "week", required = false)
                               @Parameter(description = "Получить за неделю") boolean week
                             , @RequestParam(value = "month", required = false)
                               @Parameter(description = "Получить за месяц") boolean month) {
        return client.getAll(startPeriod, endPeriod, week, month);
    }

    @Operation(summary = "Объединение шаблона документа и результатов измерений в документ")
    @GetMapping("/create/{id}")
    public Mono<Object> create(@PathVariable(value = "id") @NotNull @Positive
                               @Parameter(description = "Идентификатор записи рабочего журнала") Long workJournalId) {
        return client.create(workJournalId);
    }
}