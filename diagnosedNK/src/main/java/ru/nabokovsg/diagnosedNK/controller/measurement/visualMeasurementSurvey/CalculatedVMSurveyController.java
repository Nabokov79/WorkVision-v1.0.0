package ru.nabokovsg.diagnosedNK.controller.measurement.visualMeasurementSurvey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.visualMeasuringSurvey.ResponseCalculatedVMSurveyDto;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.CalculatedVMSurveyService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/measurement/vms",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные визуального и измерительного контроля при проведении обследования оборудования",
        description="API для работы с данными визуального и измерительного контроля " +
                "при проведении обследования оборудования")
public class CalculatedVMSurveyController {

    private final CalculatedVMSurveyService service;

    @Operation(summary = "Получить данные визуального и измерительного контроля по идентификатору записи рабочего журнала")
    @GetMapping("/{id}")
    public ResponseEntity<List<ResponseCalculatedVMSurveyDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }
}