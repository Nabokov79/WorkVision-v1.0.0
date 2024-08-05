package ru.nabokovsg.laboratoryNK.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.laboratoryNK.dto.workJournal.ResponseWorkJournalDto;
import ru.nabokovsg.laboratoryNK.dto.workJournal.WorkJournalDto;
import ru.nabokovsg.laboratoryNK.service.WorkJournalService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(
        value = "/laboratory/nk/journal/work",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Журнал выполненных работ",
        description="API для работы с данными журнала выполненных работ")
public class WorkJournalController {

    private final WorkJournalService service;

    @Operation(summary = "Добавление записи в журнал")
    @PostMapping
    public ResponseEntity<ResponseWorkJournalDto> save(
            @RequestBody @Parameter(description = "Информация об обследовании") WorkJournalDto journalDto) {
        return ResponseEntity.ok().body(service.save(journalDto));
    }

    @Operation(summary = "Изменение записи в журнале")
    @PatchMapping
    public ResponseEntity<ResponseWorkJournalDto> update(
            @RequestBody @Parameter(description = "Информация об обследовании") WorkJournalDto journalDto) {
        return ResponseEntity.ok().body(service.update(journalDto));
    }

    @Operation(summary = "Получение записей журнала")
    @GetMapping("/all")
    public ResponseEntity<List<ResponseWorkJournalDto>> getAll(
                                                  @RequestParam(value = "startPeriod", required = false)
                                                  @Parameter(description = "Начало периода") LocalDate startPeriod
                                                , @RequestParam(value = "endPeriod", required = false)
                                                  @Parameter(description = "Окончание периода") LocalDate endPeriod) {
        return ResponseEntity.ok().body(service.getAll(startPeriod, endPeriod));
    }

    @Operation(summary = "Удаление записи в журнале")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Запись журнала успешно удалена.");
    }
}