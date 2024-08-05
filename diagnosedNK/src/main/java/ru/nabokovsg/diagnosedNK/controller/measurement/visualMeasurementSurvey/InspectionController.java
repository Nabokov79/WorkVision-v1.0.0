package ru.nabokovsg.diagnosedNK.controller.measurement.visualMeasurementSurvey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.InspectionDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.inspection.ResponseInspectionDto;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.InspectionService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/measurement/inspection",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные визуального осмотра элемента(подэлемента) оборудования",
        description="API для работы с данными визуального осмотра элемента(подэлемента) оборудования")
public class InspectionController {

    private final InspectionService service;

    @Operation(summary = "Добавить данные визуального осмотра")
    @PostMapping
    public ResponseEntity<ResponseInspectionDto> save(@RequestBody
                                                      @Parameter(name = "Данные визуального осмотра")
                                                      InspectionDto inspectionDto) {
        return ResponseEntity.ok().body(service.save(inspectionDto));
    }

    @Operation(summary = "Получить данные визуального осмотра по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseInspectionDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }
}