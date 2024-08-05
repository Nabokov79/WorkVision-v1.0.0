package ru.nabokovsg.documentNK.controller.template.reportSurvey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.report.ResponseReportTemplateDto;
import ru.nabokovsg.documentNK.dto.template.reportSurvey.report.ShortResponseReportTemplateDto;
import ru.nabokovsg.documentNK.service.template.reportSurvey.ReportTemplateService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/template/report",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Шаблон отчета",
        description="API для работы с данными шаблона отчета")
public class ReportTemplateController {

    private final ReportTemplateService service;

    @Operation(summary = "Получить шаблон отчета")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseReportTemplateDto> get(@PathVariable @Parameter(name = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить все шаблоны отчетов")
    @GetMapping("/all")
    public ResponseEntity<List<ShortResponseReportTemplateDto>> getAll() {
        return ResponseEntity.ok().body(service.getAll());
    }
}