package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.calculatedVMSurvey.completedRepair.ResponseCompletedRepairDto;
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
import ru.nabokovsg.diagnosedNK.service.norms.ElementRepairService;

import java.util.List;
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

    @Override
    public ResponseCompletedRepairDto save(CompletedRepairDto repairDto) {
        CompletedRepair repair = requestService.getCompletedRepair(repairDto);

        if (repair == null) {
            ElementRepair elementRepair = elementRepairService.getById(repairDto.getRepairId());
            EquipmentElement element = elementService.get(repairDto.getElementId());
            repair = mapper.mapToCompletedRepair(elementRepair);
            if (repairDto.getPartElementId() != null) {
                EquipmentPartElement partElement = element.getPartsElement()
                        .stream()
                        .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                        .get(repairDto.getPartElementId());
                mapper.mapWithEquipmentPartElement(repair, partElement);
            }
            setQuantity(repair, repairDto);
            repair = repository.save(repair);
            repair.setParameterMeasurements(parameterMeasurementService.save(elementRepair.getMeasuredParameters()
                                                                           , repairDto.getParameterMeasurements()));
        } else {
            setQuantity(repair, repairDto);
            repair = repository.save(repair);
        }
        return mapper.mapToResponseCompletedRepairDto(repair);
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
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Completed repair with id=%s not found for delete", id));
    }


    private void setQuantity(CompletedRepair repair, CompletedRepairDto repairDto) {
        if (repair.getParameterMeasurements() == null) {
            mapper.mapToWithQuantity(repair, calculationService.getQuantity(null
                    , repairDto.getParameterMeasurements()));
            return;
        }
        mapper.mapToWithQuantity(repair
                , calculationService.getQuantity(repair.getParameterMeasurements()
                        , repairDto.getParameterMeasurements()));
    }
}