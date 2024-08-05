package ru.nabokovsg.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.company.dto.address.AddressDto;
import ru.nabokovsg.company.dto.address.ResponseAddressDto;
import ru.nabokovsg.company.service.AddressService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/address",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Адрес",
        description = "API для работы с данными адреса")
public class AddressController {

    private final AddressService service;

    @Operation(summary = "Добавление нового адреса")
    @PostMapping
    public ResponseEntity<ResponseAddressDto> save(
                                                @RequestBody @Parameter(description = "Адрес") AddressDto addressDto) {
        return ResponseEntity.ok().body(service.save(addressDto));
    }

    @Operation(summary = "Изменение данных адреса")
    @PatchMapping
    public ResponseEntity<ResponseAddressDto> update(
                                                @RequestBody @Parameter(description = "Адрес") AddressDto addressDto) {
        return ResponseEntity.ok().body(service.update(addressDto));
    }

    @Operation(summary = "Получение всех адресов")
    @GetMapping("/all")
    public ResponseEntity<List<ResponseAddressDto>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }

    @Operation(summary = "Удаление адреса")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok( "Адрес успешно удален.");
    }
}