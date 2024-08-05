package ru.nabokovsg.documentNK.controller.document;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.service.document.report.ReportSurveyService;

@RestController
@RequestMapping(
        value = "/document/survey/report",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Приложения документа",
     description="API для работы с данными приложений документа")
public class ReportSurveyController {

    private final ReportSurveyService service;

    @Operation(summary = "Создать отчет по итогам обследования")
    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Parameter(name = "Данные для создания документа")
                                         DocumentCreationDataDto documentCreationDataDto) {
        return ResponseEntity.ok().body(service.create(documentCreationDataDto));
    }
}