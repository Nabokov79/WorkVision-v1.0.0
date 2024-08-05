package ru.nabokovsg.documentNK.controller.document;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.documentNK.dto.document.informationAboutObjectOfControl.InformationAboutObjectOfControlDto;
import ru.nabokovsg.documentNK.dto.document.informationAboutObjectOfControl.ResponseInformationAboutObjectOfControlDto;
import ru.nabokovsg.documentNK.service.document.protocol.InformationAboutObjectOfControlService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/document/protocol/control/information/object",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Сведения об объекте контроля",
        description="API для работы с данными сведений об объекте контроля")
public class InformationAboutObjectOfControlController {

    private final InformationAboutObjectOfControlService service;

    @Operation(summary = "Новые сведения об объекте контроля")
    @PostMapping
    public ResponseEntity<ResponseInformationAboutObjectOfControlDto> save(
                                                          @RequestBody
                                                          @Parameter(description = "Сведения об объекте контроля")
                                                                InformationAboutObjectOfControlDto informationDto) {
        return ResponseEntity.ok().body(service.save(informationDto));
    }

    @Operation(summary = "Изменения сведений об объекте контроля")
    @PatchMapping
    public ResponseEntity<ResponseInformationAboutObjectOfControlDto> update(
                                                              @RequestBody
                                                              @Parameter(description = "Сведения об объекте контроля")
                                                                    InformationAboutObjectOfControlDto informationDto) {
        return ResponseEntity.ok().body(service.update(informationDto));
    }

    @Operation(summary = "Получить сведения об объекте контроля")
    @GetMapping("/all{id}")
    public ResponseEntity<List<ResponseInformationAboutObjectOfControlDto>> getAll(
                                                 @PathVariable(name = "id")
                                                 @Parameter(description = "Идентификатор протокола") Long protocolId) {
        return ResponseEntity.ok().body(service.getAll(protocolId));
    }

    @Operation(summary = "Удаление сведения об объекте контроля")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @Parameter(description = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Сведения об объекте контроля удалены.");
    }
}