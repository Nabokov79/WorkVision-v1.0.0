package ru.nabokovsg.diagnosedNK.controller.measurement.visualMeasurementSurvey.detected;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.identifiedDefect.IdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.identifiedDefect.ResponseIdentifiedDefectDto;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected.IdentifiedDefectService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/measurement/defect",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные обнаруженных дефектов элементов, подэлементов оборудования",
        description="API для работы с данными дефектов элементов, подэлементов оборудования")
public class IdentifiedDefectController {

    private final IdentifiedDefectService service;

    @Operation(summary = "Добавить данные измеренного дефекта")
    @PostMapping
    public ResponseEntity<ResponseIdentifiedDefectDto> save(@RequestBody
                                                             @Parameter(name = "Данные измеренного дефекта")
                                                            IdentifiedDefectDto defectDto) {
        return ResponseEntity.ok().body(service.save(defectDto));
    }

    @Operation(summary = "Получить данные измеренных дефектов элементов(подэлементов) оборудования" +
                        " по идентификатору записи рабочего журнала")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseIdentifiedDefectDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удалить измеренный дефект")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные дефекта успешно удалены.");
    }
}