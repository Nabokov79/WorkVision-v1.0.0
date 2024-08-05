package ru.nabokovsg.gateway.controller.laboratoryNK;

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
import ru.nabokovsg.gateway.client.laboratoryNK.RemarkClient;
import ru.nabokovsg.gateway.dto.laboratoryNK.documentRemark.NewRemarkDto;
import ru.nabokovsg.gateway.dto.laboratoryNK.documentRemark.UpdateRemarkDto;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/nk/document/remark",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Замечания к документам по результатам обследования",
        description="API для работы с данными замечаний к выполненным документам")
public class RemarkController {

    private final RemarkClient client;

    @Operation(summary = "Добавление нового замечания ")
    @PostMapping
    public Mono<Object> save(@RequestBody @Valid @Parameter(description = "Замечание") NewRemarkDto remarkDto) {
        return client.save(remarkDto);
    }

    @Operation(summary = "Изменение данных замечания")
    @PatchMapping
    public  Mono<Object> update(@RequestBody @Valid @Parameter(description = "Замечание") UpdateRemarkDto remarkDto) {
        return client.update(remarkDto);
    }

    @Operation(summary = "Получение замечаний")
    @GetMapping("/all/{id}")
    public Flux<Object> getAll(@PathVariable @NotNull @Positive
                               @Parameter(description = "Идентификатор сотрудника") Long id) {
        return client.getAll(id);
    }

    @Operation(summary = "Удаление замечания")
    @DeleteMapping("/{id}")
    public Mono<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        return client.delete(id);
    }
}