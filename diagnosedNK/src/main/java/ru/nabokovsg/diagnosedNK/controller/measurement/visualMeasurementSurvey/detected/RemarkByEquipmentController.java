package ru.nabokovsg.diagnosedNK.controller.measurement.visualMeasurementSurvey.detected;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.remarkByEquipment.RemarkByEquipmentDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.remarkByEquipment.ResponseRemarkByEquipmentDto;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected.RemarkByEquipmentService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/measurement/inspection",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Данные замечания по техническому состоянию элементов/подэлементов оборудования",
        description="API для работы с данными замечания по техническому состоянию элементов/подэлементов оборудования")
public class RemarkByEquipmentController {

    private final RemarkByEquipmentService service;

    @Operation(summary = "Добавить данные замечания")
    @PostMapping
    public ResponseEntity<ResponseRemarkByEquipmentDto> save(@RequestBody
                                                      @Parameter(name = "Данные замечания")
                                                      RemarkByEquipmentDto inspectionDto) {
        return ResponseEntity.ok().body(service.save(inspectionDto));
    }

    @Operation(summary = "Изменить данные замечания")
    @PatchMapping
    public ResponseEntity<ResponseRemarkByEquipmentDto> update(@RequestBody
                                                             @Parameter(name = "Данные замечания")
                                                             RemarkByEquipmentDto inspectionDto) {
        return ResponseEntity.ok().body(service.save(inspectionDto));
    }

    @Operation(summary = "Получить данные замечаний по идентификатору записи оборудования")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseRemarkByEquipmentDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }
}