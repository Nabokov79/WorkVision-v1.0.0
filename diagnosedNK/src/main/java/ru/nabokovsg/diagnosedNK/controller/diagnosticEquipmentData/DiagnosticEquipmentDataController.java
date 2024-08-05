package ru.nabokovsg.diagnosedNK.controller.diagnosticEquipmentData;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nabokovsg.diagnosedNK.dto.diagnosticEquipmentData.EquipmentDto;
import ru.nabokovsg.diagnosedNK.service.diagnosticEquipmentData.DiagnosticEquipmentDataService;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/equipment",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Добавление данных объекта(оборудования) обследования",
        description="API для работы с данными объекта диагностирования" +
                ", добавление данных для выполнения расчета результатов измерений")
public class DiagnosticEquipmentDataController {

    private final DiagnosticEquipmentDataService service;

    @Operation(summary = "Добавление данных объекта(оборудования) обследования")
    @PostMapping
    public ResponseEntity<HttpStatus> save(@RequestBody @Parameter(name = "Данные объекта") EquipmentDto equipmentDto) {
        service.save(equipmentDto);
        return ResponseEntity.ok().build();
    }
}