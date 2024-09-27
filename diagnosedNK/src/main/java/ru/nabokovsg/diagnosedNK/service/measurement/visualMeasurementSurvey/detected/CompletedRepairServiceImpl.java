package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.detected;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.completedRepair.ResponseCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.parameterMeasurement.ParameterMeasurementDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.detected.CompletedRepairMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentElement;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPartElement;
import ru.nabokovsg.diagnosedNK.model.measurement.enums.TypeVMSData;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.builders.ParameterMeasurementBuilder;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.detected.QCompletedRepair;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.detected.CompletedRepairRepository;
import ru.nabokovsg.diagnosedNK.service.equipment.EquipmentElementService;
import ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey.calculated.CalculatedCompletedRepairService;
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
    private final CalculatedCompletedRepairService calculatedRepairService;
    private final EntityManager em;

    @Override
    public ResponseCompletedRepairDto save(CompletedRepairDto repairDto) {
        ElementRepair elementRepair = elementRepairService.getById(repairDto.getRepairId());
        Set<CompletedRepair> repairs = getAllByPredicate(repairDto);
        CompletedRepair repair = searchDuplicate(repairDto, repairs);
        if (repair == null) {
            repair = repository.save(createCompletedRepair(repairDto, elementRepair));
            repair.setParameterMeasurements(parameterService.save(
                    new ParameterMeasurementBuilder.Builder()
                                                   .typeData(TypeVMSData.REPAIR)
                                                   .repair(repair)
                                                   .measuredParameter(elementRepair.getMeasuredParameters())
                                                   .parameterMeasurements(repairDto.getParameterMeasurements())
                                                   .build()));
        }
        calculatedRepairService.save(repairs, repair, elementRepair);
        return mapper.mapToResponseCompletedRepairDto(repair);
    }

    @Override
    public ResponseCompletedRepairDto update(CompletedRepairDto repairDto) {
        ElementRepair elementRepair = elementRepairService.getById(repairDto.getRepairId());
        Map<Long, CompletedRepair> repairs = getAllByPredicate(repairDto)
                                                            .stream()
                                                            .collect(Collectors.toMap(CompletedRepair::getId, d -> d));
        CompletedRepair repair = repairs.get(repairDto.getId());
        if (repair == null) {
                repair = repository.save(createCompletedRepair(repairDto, elementRepair));
                repair.setParameterMeasurements(parameterService.update(repair.getParameterMeasurements()
                                                                       , repairDto.getParameterMeasurements()));

        } else {
            delete(repairDto.getId());
        }
        calculatedRepairService.update(new HashSet<>(repairs.values()), repair, elementRepair);
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
        CompletedRepair repair = get(id);
        parameterService.deleteAll(repair.getParameterMeasurements());
        repository.deleteById(id);
    }

    private CompletedRepair get(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("CompletedRepair defect with id=%s not found", id)));
    }

    private CompletedRepair searchDuplicate(CompletedRepairDto repairDto, Set<CompletedRepair> repairs) {
        Map<Long, CompletedRepair> repairsDb = repairs.stream()
                                                      .collect(Collectors.toMap(CompletedRepair::getId, d -> d));
        Map<Long, ParameterMeasurementDto> parametersDto = repairDto.getParameterMeasurements()
                .stream()
                .collect(Collectors.toMap(ParameterMeasurementDto::getParameterId, p -> p));
        boolean flag;
        for (CompletedRepair repair : repairsDb.values()) {
            flag = parameterService.searchParameterDuplicate(repair.getParameterMeasurements(), parametersDto);
            if (flag) {
                return repair;
            }
        }
        return null;
    }

    private Set<CompletedRepair> getAllByPredicate(CompletedRepairDto repairDto) {
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

    private CompletedRepair createCompletedRepair(CompletedRepairDto repairDto, ElementRepair elementRepair) {
        EquipmentElement element = elementService.get(repairDto.getElementId());
        CompletedRepair repair = mapper.mapToCompletedRepair(repairDto, elementRepair, element);
        if (repairDto.getPartElementId() != null) {
            EquipmentPartElement partElement = element.getPartsElement()
                    .stream()
                    .collect(Collectors.toMap(EquipmentPartElement::getPartElementId, p -> p))
                    .get(repairDto.getPartElementId());
            mapper.mapWithEquipmentPartElement(repair, partElement);
        }
        return repair;
    }
}