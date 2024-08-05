package ru.nabokovsg.diagnosedNK.controller.measurement.geodesy;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nabokovsg.diagnosedNK.dto.measurement.geodesy.ResponseEquipmentGeodesicMeasurementsDto;
import ru.nabokovsg.diagnosedNK.service.measurement.geodesy.EquipmentGeodesicMeasurementsService;

@RestController
@RequestMapping(
        value = "/diagnosed/nk/calculated/geodesy",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Рассчитанные данные измерений геодезической съемки(нивелировании) оборудования",
        description="API для работы с рассчитанными данными измерений геодезической съемки(нивелировании) оборудования")
public class EquipmentGeodesicMeasurementsController {

    private final EquipmentGeodesicMeasurementsService service;

    @Operation(summary = "Получить рассчитанные данные геодезических измерений по идентификатору записи журнала задач")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseEquipmentGeodesicMeasurementsDto> get(
            @PathVariable(name = "id") @Parameter(name = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.get(equipmentId));
    }
}