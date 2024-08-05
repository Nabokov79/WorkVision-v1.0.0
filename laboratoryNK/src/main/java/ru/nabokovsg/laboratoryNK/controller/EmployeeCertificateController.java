package ru.nabokovsg.laboratoryNK.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate.EmployeeCertificateDto;
import ru.nabokovsg.laboratoryNK.dto.laboratoryEmployee.employeeCertificate.ResponseEmployeeCertificateDto;
import ru.nabokovsg.laboratoryNK.service.EmployeeCertificateService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/laboratory/nk/employee/certificates",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Аттестация сотрудника",
     description="API для работы с данными аттестации сотрудников лаборатории НК")
public class EmployeeCertificateController {

    private final EmployeeCertificateService service;

    @Operation(summary = "Добавление данных сертификатов сотрудника")
    @PostMapping
    public ResponseEntity<ResponseEmployeeCertificateDto> save(
               @RequestBody @Parameter(description = "Данные аттестации") EmployeeCertificateDto certificateDto) {
        return ResponseEntity.ok().body(service.save(certificateDto));
    }

    @Operation(summary = "Изменение данных аттестации сотрудника")
    @PatchMapping
    public ResponseEntity<ResponseEmployeeCertificateDto> update(
                @RequestBody @Parameter(description = "Данные аттестации") EmployeeCertificateDto certificateDto) {
        return ResponseEntity.ok().body(service.update(certificateDto));
    }

    @Operation(summary = "Получение данных аттестации сотрудника")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseEmployeeCertificateDto>> getAll(
                        @PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        return ResponseEntity.ok().body(service.getAll(id));
    }

    @Operation(summary = "Удаление данных аттестации сотрудника")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Аттестация сотрудника успешно удалена.");
    }
}