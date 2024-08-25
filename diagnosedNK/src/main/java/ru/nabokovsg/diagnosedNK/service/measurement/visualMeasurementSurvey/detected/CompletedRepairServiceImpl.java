package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair.ResponseCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.CompletedRepairMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.CompletedRepairRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;
import ru.nabokovsg.diagnosedNK.service.measurement.CalculationMeasurementService;
import ru.nabokovsg.diagnosedNK.service.measurement.QueryDSLRequestService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.CalculatedRepairService;
import ru.nabokovsg.diagnosedNK.service.norms.ElementRepairService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompletedRepairServiceImpl implements CompletedRepairService {

    private final CompletedRepairRepository repository;
    private final CompletedRepairMapper mapper;
    private final ElementRepairService elementRepairService;
    private final ParameterMeasurementService parameterMeasurementService;
    private final QueryDSLRequestService requestService;
    private final EquipmentElementService elementService;
    private final CalculationMeasurementService calculationService;
    private final CalculatedRepairService calculatedRepairService;

    @Override
    public ResponseCompletedRepairDto save(CompletedRepairDto repairDto) {
        CompletedRepair repair = requestService.getCompletedRepair(repairDto);
        ElementRepair elementRepair = elementRepairService.getById(repairDto.getRepairId());
        if (repair == null) {
            EquipmentElement element = elementService.get(repairDto.getElementId());
            repair = mapper.mapToCompletedRepair(repairDto, elementRepair, element);
            if (repairDto.getPartElementId() != null) {
                EquipmentPartElement partElement = element.getPartsElement()
                        .stream()
                        .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                        .get(repairDto.getPartElementId());
                mapper.mapWithEquipmentPartElement(repair, partElement);
            }
            mapper.mapToWithQuantity(repair, calculationService.getQuantity(null, repairDto.getQuantity()));
            repair = repository.save(repair);
            repair.setParameterMeasurements(parameterMeasurementService.saveForCompletedRepair(
                                                                              repair
                                                                            , elementRepair.getMeasuredParameters()
                                                                            , repairDto.getParameterMeasurements()));
        } else {
            mapper.mapToWithQuantity(repair, calculationService.getQuantity(repair.getQuantity()
                                                                          , repairDto.getQuantity()));
            repair = repository.save(repair);
        }
        calculatedRepairService.save(requestService.getAllCompletedRepair(repairDto), repair, elementRepair);
        return mapper.mapToResponseCompletedRepairDto(repair);
    }

    @Override
    public ResponseCompletedRepairDto update(CompletedRepairDto repairDto) {
        Map<Long, CompletedRepair> repairs = requestService.getAllCompletedRepair(repairDto)
                .stream()
                .collect(Collectors.toMap(CompletedRepair::getId, d -> d));
        CompletedRepair repairDb = requestService.getCompletedRepair(repairDto);
        ElementRepair elementRepair = elementRepairService.getById(repairDto.getRepairId());
        if (repairDb == null) {
            repairDb = repairs.get(repairDto.getId());
            repairDb =  mapper.mapToWithQuantity(repairDb, calculationService.getQuantity(repairDb.getQuantity()
                    , repairDto.getQuantity()));
            repairDb.setParameterMeasurements(parameterMeasurementService.update(repairDb.getParameterMeasurements()
                    , repairDto.getParameterMeasurements()));
        } else {
            repairDb =  mapper.mapToWithQuantity(repairDb, calculationService.getQuantity(repairDb.getQuantity()
                    ,repairDto.getQuantity()));
            delete(repairDto.getId());
            repairs.remove(repairDto.getId());
        }
        repairs.put(repairDb.getId(),repairDb);
        calculatedRepairService.update(new HashSet<>(repairs.values()), repairDb, elementRepair);
        return mapper.mapToResponseCompletedRepairDto(repairDb);
    }

    @Override
    public List<ResponseCompletedRepairDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                                      .stream()
                                      .map(mapper::mapToResponseCompletedRepairDto)
                                      .toList();
    }

    @Override
    public void delete(Long id) {
        CompletedRepair repair = get(id);
        parameterMeasurementService.deleteAll(repair.getParameterMeasurements());
        repository.deleteById(id);
    }

    private CompletedRepair get(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("CompletedRepair defect with id=%s not found", id)));
    }
}