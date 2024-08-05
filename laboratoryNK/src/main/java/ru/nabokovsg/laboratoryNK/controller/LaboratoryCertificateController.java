package ru.nabokovsg.laboratoryNK.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.laboratoryNK.dto.laboratoryCertificate.LaboratoryCertificateDto;
import ru.nabokovsg.laboratoryNK.service.LaboratoryCertificateService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/laboratory/nk/certificate",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Аттестация лаборатории",
        description="API для работы с данными аттестации лаборатории НК")
public class LaboratoryCertificateController {

    private final LaboratoryCertificateService service;

    @Operation(summary = "Добавление сведений об аттестации")
    @PostMapping
    public ResponseEntity<LaboratoryCertificateDto> save(
            @RequestBody
            @Parameter(description = "Сведения об аттестации") LaboratoryCertificateDto certificateDto) {
        return ResponseEntity.ok().body(service.save(certificateDto));
    }

    @Operation(summary = "Изменение сведений об аттестации")
    @PatchMapping
    public ResponseEntity<LaboratoryCertificateDto> update(
            @RequestBody
            @Parameter(description = "Сведения об аттестации") LaboratoryCertificateDto certificateDto) {
        return ResponseEntity.ok().body(service.update(certificateDto));
    }

    @Operation(summary = "Получение всех сохраненных аттестаций")
    @GetMapping
    public ResponseEntity<List<LaboratoryCertificateDto>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Удаление сведений об аттестации")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Сведения об аттестации успешно удалены.");
    }
}