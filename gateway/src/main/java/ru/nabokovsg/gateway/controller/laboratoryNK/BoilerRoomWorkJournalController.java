package ru.nabokovsg.gateway.controller.laboratoryNK;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.laboratoryNK.BoilerRoomWorkJournalClient;
import ru.nabokovsg.gateway.dto.laboratoryNK.workJournal.NewBoilerRoomWorkJournalWorkJournalDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.workJournal.UpdateBoilerRoomWorkJournalDto;

import java.time.LocalDate;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/journal/work/boiler",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Рабочий журнал обследований объектов котельных",
        description="API для работы с данными журнала обследований объектов котельных")
public class BoilerRoomWorkJournalController {

    private final BoilerRoomWorkJournalClient client;

    @Operation(summary = "Добавление записи в журнал")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                            @Parameter(description = "Информация об обследовании")
                             NewBoilerRoomWorkJournalWorkJournalDto journalDto) {
        return client.save(journalDto);
    }

    @Operation(summary = "Изменение записи в журнале")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid
                               @Parameter(description = "Информация об обследовании")
                               UpdateBoilerRoomWorkJournalDto journalDto) {
        return client.update(journalDto);
    }

    @Operation(summary = "Получение записей журнала")
    @GetMapping
    public Flux<Object> getAll(@RequestParam(value = "startPeriod", required = false)
                               @Parameter(description = "Начало периода") LocalDate startPeriod
            , @RequestParam(value = "endPeriod", required = false)
                               @Parameter(description = "Окончание периода") LocalDate endPeriod) {
        return client.getAll(startPeriod, endPeriod);
    }

    @Operation(summary = "Удаление записи в журнале")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}