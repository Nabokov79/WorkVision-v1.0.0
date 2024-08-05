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
import ru.nabokovsg.gateway.client.company.OrganizationClient;
import ru.nabokovsg.gateway.dto.company.organization.NewOrganizationDto;
import ru.nabokovsg.gateway.dto.company.organization.UpdateOrganizationDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/organization",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Организация",
        description="API для работы с данными организации")
public class OrganizationController {

    private final OrganizationClient client;

    @Operation(summary = "Добавление данных организации")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid
                                 @Parameter(description = "Организация") NewOrganizationDto organizationDto) {
        return client.save(organizationDto);
    }

    @Operation(summary = "Изменение данных организации")
    @PatchMapping
    public Mono<Object> updateOrganization(@RequestBody @Valid
                                           @Parameter(description = "Организация") UpdateOrganizationDto organizationDto) {
        return client.update(organizationDto);
    }

    @Operation(summary = "Получение полных данных организации")
    @GetMapping("/{id}")
    public Mono<Object> get(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.get(id);
    }

    @Operation(summary = "Получение кратких сведений об организациях")
    @GetMapping
    public Flux<Object> getAll() {
        return client.getAll();
    }

    @Operation(summary = "Удаление данных организации")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}