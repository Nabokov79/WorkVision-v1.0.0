package ru.nabokovsg.diagnosedNK.service.measurement.visualMeasurementSurvey;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair.CompletedRepairDto;
import ru.nabokovsg.diagnosedNK.dto.measurement.visualMeasurementSurvey.completedRepair.ResponseCompletedRepairDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.measurement.visualMeasurementSurvey.CompletedRepairMapper;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.CompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.QCompletedRepair;
import ru.nabokovsg.diagnosedNK.model.measurement.visualMeasurementSurvey.QVisualMeasuringSurvey;
import ru.nabokovsg.diagnosedNK.model.norms.ElementRepair;
import ru.nabokovsg.diagnosedNK.repository.measurement.visualMeasurementSurvey.CompletedRepairRepository;
import ru.nabokovsg.diagnosedNK.service.measurement.QueryDSLRequestService;
import ru.nabokovsg.diagnosedNK.service.norms.ElementRepairService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompletedRepairServiceImpl implements CompletedRepairService {

    private final CompletedRepairRepository repository;
    private final CompletedRepairMapper mapper;
    private final EntityManager em;
    private final ElementRepairService elementRepairService;
    private final ParameterMeasurementService parameterMeasurementService;
    private final VisualMeasuringSurveyService visualMeasuringSurveyService;
    private final ExaminedPartElementService examinedPartElementService;
    private final QueryDSLRequestService requestService;

    @Override
    public ResponseCompletedRepairDto save(CompletedRepairDto repairDto) {
        CompletedRepair repair = requestService.getCompletedRepair(repairDto);
        ElementRepair elementRepair = elementRepairService.getById(repair.getRepairId());
        if (repair != null) {
            repair = mapper.mapToCompletedRepair(elementRepair);
            if (repairDto.getPartElementId() != null) {
                repair = mapper.mapWithExaminedPartElement(repair
                                                         , examinedPartElementService.get(repairDto.getEquipmentId()
                                                                                        , repairDto.getElementId()));
            } else {
                repair = mapper.mapWithVisualMeasuringSurvey(repair
                        , visualMeasuringSurveyService.get(repairDto.getEquipmentId()
                                                         , repairDto.getElementId()));
            }
            repair = repository.save(repair);
        }
        repair.setParameterMeasurements(parameterMeasurementService.save(elementRepair.getCalculation()
                                                                       , elementRepair.getMeasuredParameters()
                                                                       , repair.getParameterMeasurements()
                                                                       , repairDto.getParameterMeasurements()));
        return mapper.mapToResponseCompletedRepairDto(repair);
    }

    @Override
    public List<ResponseCompletedRepairDto> getAll(Long equipmentId) {
        QCompletedRepair repair = QCompletedRepair.completedRepair;
        QVisualMeasuringSurvey vms = QVisualMeasuringSurvey.visualMeasuringSurvey;
        return new JPAQueryFactory(em).from(repair)
                                      .select(repair)
                                      .where(vms.equipmentId.eq(equipmentId))
                                      .fetch()
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
}