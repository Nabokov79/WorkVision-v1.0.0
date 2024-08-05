package ru.nabokovsg.diagnosedNK.controller.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.diagnosedNK.dto.common.RecommendationEmployeeDto;
import ru.nabokovsg.diagnosedNK.dto.common.ResponseRecommendationEmployeeDto;
import ru.nabokovsg.diagnosedNK.service.common.RecommendationEmployeeService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/recommendation",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Добавление рекомендаций по эксплуатации оборудования",
        description="API для работы с рекомендаций по эксплуатации оборудования")
public class RecommendationEmployeeController {

    private final RecommendationEmployeeService service;

    @Operation(summary = "Новая рекомендация для раздела отчета")
    @PostMapping
    public ResponseEntity<ResponseRecommendationEmployeeDto> save(
                          @RequestBody @Parameter(name = "Рекомендация") RecommendationEmployeeDto recommendationDto) {
        return ResponseEntity.ok().body(service.save(recommendationDto));
    }

    @Operation(summary = "Изменение рекомендации")
    @PatchMapping
    public ResponseEntity<ResponseRecommendationEmployeeDto> update(
                          @RequestBody @Parameter(name = "Рекомендация") RecommendationEmployeeDto recommendationDto) {
        return ResponseEntity.ok().body(service.update(recommendationDto));
    }

    @Operation(summary = "Получить рекомендации по типу объекта")
    @GetMapping("/all/{id}")
    public ResponseEntity<List<ResponseRecommendationEmployeeDto>> getAll(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удалить рекомендацию")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Рекомендация успешно удалены.");
    }
}