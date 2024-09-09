package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair.ResponseCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.CompletedRepairMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.ParameterMeasurement;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.QCompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.CompletedRepairRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.CalculatedRepairService;
import ru.nabokovsg.diagnosedNK.service.norms.ElementRepairService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompletedRepairServiceImpl implements CompletedRepairService {

    private final CompletedRepairRepository repository;
    private final CompletedRepairMapper mapper;
    private final ElementRepairService elementRepairService;
    private final ParameterMeasurementService parameterService;
    private final EquipmentElementService elementService;
    private final CalculatedRepairService calculatedRepairService;
    private final EntityManager em;

    @Override
    public ResponseCompletedRepairDto save(CompletedRepairDto repairDto) {
        ElementRepair elementRepair = elementRepairService.getById(repairDto.getRepairId());
        Set<CompletedRepair> repairs = getAllByPredicate(repairDto);
        CompletedRepair repair = searchDuplicate(repairDto, elementRepair, repairs);
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
            repair = repository.save(repair);
            repair.setParameterMeasurements(parameterService.saveForCompletedRepair(
                                                                              repair
                                                                            , elementRepair.getMeasuredParameters()
                                                                            , repairDto.getParameterMeasurements()));
        }
        calculatedRepairService.save(repairs, repair, elementRepair);
        return mapper.mapToResponseCompletedRepairDto(repair);
    }

    @Override
    public ResponseCompletedRepairDto update(CompletedRepairDto repairDto) {
        Map<Long, CompletedRepair> repairs = getAllByPredicate(repairDto)
                                                            .stream()
                                                            .collect(Collectors.toMap(CompletedRepair::getId, d -> d));
        CompletedRepair repairDb = repairs.get(repairDto.getId());
        ElementRepair elementRepair = elementRepairService.getById(repairDto.getRepairId());
        if (repairDb == null) {
            repairDb = repairs.get(repairDto.getId());
            repairDb.setParameterMeasurements(parameterService.update(repairDb.getParameterMeasurements()
                    , repairDto.getParameterMeasurements()));
        } else {
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
        parameterService.deleteAll(repair.getParameterMeasurements());
        repository.deleteById(id);
    }

    private CompletedRepair get(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("CompletedRepair defect with id=%s not found", id)));
    }

    private CompletedRepair searchDuplicate(CompletedRepairDto repairDto
                                          , ElementRepair elementRepair
                                          , Set<CompletedRepair> repairs) {
        Map<Long, CompletedRepair> repairsDb = repairs.stream()
                                                      .collect(Collectors.toMap(CompletedRepair::getId, d -> d));
        Map<String, ParameterMeasurement> parameters = parameterService.map(repairDto.getParameterMeasurements()
                                                                          , elementRepair.getMeasuredParameters())
                .stream()
                .collect(Collectors.toMap(ParameterMeasurement::getParameterName, p -> p));
        boolean flag;
        for (CompletedRepair repair : repairsDb.values()) {
            flag = parameterService.searchParameterDuplicate(repair.getParameterMeasurements()
                    , parameters);
            if (flag) {
                return repair;
            }
        }
        return null;
    }

    public Set<CompletedRepair> getAllByPredicate(CompletedRepairDto repairDto) {
        QCompletedRepair repair = QCompletedRepair.completedRepair;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(repair.equipmentId.eq(repairDto.getEquipmentId()));
        builder.and(repair.repairId.eq(repairDto.getRepairId()));
        builder.and(repair.elementId.eq(repairDto.getElementId()));
        if (repairDto.getPartElementId() != null) {
            builder.and(repair.partElementId.eq(repairDto.getPartElementId()));
        }
        return new HashSet<>(new JPAQueryFactory(em).from(repair)
                .select(repair)
                .where(builder)
                .fetch());
    }
}