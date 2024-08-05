package ru.nabokovsg.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.company.dto.organization.OrganizationDto;
import ru.nabokovsg.company.dto.organization.ResponseOrganizationDto;
import ru.nabokovsg.company.dto.organization.ShortResponseOrganizationDto;
import ru.nabokovsg.company.service.OrganizationService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/organization",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Организация",
        description="API для работы с данными организации")
public class OrganizationController {

    private final OrganizationService service;

    @Operation(summary = "Добавление данных организации")
    @PostMapping
    public ResponseEntity<ShortResponseOrganizationDto> save(@RequestBody
                                              @Parameter(description = "Организация") OrganizationDto organizationDto) {
        return ResponseEntity.ok().body(service.save(organizationDto));
    }

    @Operation(summary = "Изменение данных организации")
    @PatchMapping
    public ResponseEntity<ShortResponseOrganizationDto> update(@RequestBody
                                              @Parameter(description = "Организация") OrganizationDto organizationDto) {
        return ResponseEntity.ok().body(service.update(organizationDto));
    }

    @Operation(summary = "Получение полных данных организации")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrganizationDto> get(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получение кратких сведений об организациях")
    @GetMapping("/all")
    public ResponseEntity<List<ShortResponseOrganizationDto>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Удаление данных организации")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable
                                         @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Организация успешно удалена.");
    }
}