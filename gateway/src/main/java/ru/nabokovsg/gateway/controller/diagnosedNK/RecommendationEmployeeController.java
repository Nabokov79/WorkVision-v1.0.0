package ru.nabokovsg.gateway.controller.diagnosedNK;

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
import ru.nabokovsg.gateway.client.diagnosedNK.RecommendationEmployeeClient;
import ru.nabokovsg.gateway.dto.diagnosedNK.recommendationEmployee.NewRecommendationEmployeeDto;
import ru.nabokovsg.gateway.dto.diagnosedNK.recommendationEmployee.UpdateRecommendationEmployeeDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/survey/recommendation",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Добавление рекомендаций по эксплуатации оборудования",
        description="API для работы с рекомендаций по эксплуатации оборудования")
public class RecommendationEmployeeController {

    private final RecommendationEmployeeClient client;

    @Operation(summary = "Новая рекомендация для раздела отчета")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(name = "Рекомендация")
                                   NewRecommendationEmployeeDto recommendationDto) {
        return client.save(recommendationDto);
    }

    @Operation(summary = "Изменение рекомендации")
    @PatchMapping
    public Mono<Object> update(@RequestBody @Valid @Parameter(name = "Рекомендация")
                                  UpdateRecommendationEmployeeDto recommendationDto) {
        return client.update(recommendationDto);
    }

    @Operation(summary = "Получить рекомендации по типу объекта")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable(name = "id") @NotNull @Positive
                               @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return client.getAll(equipmentId);
    }

    @Operation(summary = "Удалить рекомендацию")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        return client.delete(id);
    }
}