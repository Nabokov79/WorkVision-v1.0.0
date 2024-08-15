package ru.nabokovsg.diagnosedNK.controller.measurement.visualMeasurementSurvey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.ResponseCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.CompletedRepairService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/measurement/completed/repair",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Выполненные ремонты элементов, подэлементов оборудования",
        description="API для работы с данными выполненных ремонтов элементов, подэлементов оборудования")
public class CompletedRepairController {

    private final CompletedRepairService service;

    @Operation(summary = "Добавить выполненный ремонт элемента")
    @PostMapping
    public ResponseEntity<ResponseCompletedRepairDto> save(
            @RequestBody @Parameter(description = "Выполненный ремонт") CompletedRepairDto repairDto) {
        return ResponseEntity.ok().body(service.save(repairDto));
    }

    @Operation(summary = "Получить выполненные ремонты элементов оборудования" +
                        " по идентификатору записи рабочего журнала")
    @GetMapping("/{id}")
    public ResponseEntity<List<ResponseCompletedRepairDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удалить ремонт элемента")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Выполненный ремонт элемента оборудования успешно удален.");
    }
}