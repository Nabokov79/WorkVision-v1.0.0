package ru.nabokovsg.gateway.controller.company;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.nabokovsg.gateway.client.company.AddressClient;
import ru.nabokovsg.gateway.dto.company.address.NewAddressDto;
import ru.nabokovsg.gateway.dto.company.address.UpdateAddressDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/address",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name = "Адрес",
        description = "API для работы с данными адреса")
public class AddressController {

    private final AddressClient client;

    @Operation(summary = "Добавление нового адреса")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(description = "Адрес") NewAddressDto addressDto) {
        return client.save(addressDto);
    }

    @Operation(summary = "Изменение данных адреса")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid @Parameter(description = "Адрес") UpdateAddressDto addressDto) {
        return client.update(addressDto);
    }

    @Operation(summary = "Получение всех адресов")
    @GetMapping
    public Flux<Object> getAll() {
        return client.getAll();
    }

    @Operation(summary = "Удаление адреса")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive
                               @Parameter(description = "Идентификатор адреса") Long id) {
        return client.delete(id);
    }
}