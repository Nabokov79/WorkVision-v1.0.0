package ru.nabokovsg.laboratoryNK.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.laboratoryNK.client.LaboratoryClient;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.MeasuringToolDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.ResponseMeasuringToolDto;
import ru.nabokovsg.laboratoryNK.dto.measuringTool.SearchParameters;
import ru.nabokovsg.laboratoryNK.exceptions.NotFoundException;
import ru.nabokovsg.laboratoryNK.mapper.MeasuringToolMapper;
import ru.nabokovsg.laboratoryNK.model.MeasuringTool;
import ru.nabokovsg.laboratoryNK.model.QMeasuringTool;
import ru.nabokovsg.laboratoryNK.repository.MeasuringToolRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasuringToolServiceImpl implements MeasuringToolService {

    private final MeasuringToolRepository repository;
    private final MeasuringToolMapper mapper;
    private final EntityManager em;
    private final LaboratoryClient client;

    @Override
    public ResponseMeasuringToolDto save(MeasuringToolDto measuringToolDto) {
        if (getByPredicate(measuringToolDto) == null) {
            ResponseMeasuringToolDto measuringTool = mapper.mapToResponseMeasuringToolDto(
                                                        repository.save(mapper.mapToMeasuringTool(measuringToolDto)));
            client.addMeasuringTool(measuringTool);
            return measuringTool;
        }
        throw new NotFoundException(String.format("MeasuringTool %s is found", measuringToolDto));
    }

    @Override
    public ResponseMeasuringToolDto update(MeasuringToolDto measuringToolDto) {
        if (repository.existsById(measuringToolDto.getId())) {
            ResponseMeasuringToolDto measuringTool = mapper.mapToResponseMeasuringToolDto(
                    repository.save(mapper.mapToMeasuringTool(measuringToolDto)));
            client.updateMeasuringTool(measuringTool);
            return measuringTool;
        }
        throw new NotFoundException(
                String.format("MeasuringTool with id=%s not found for update", measuringToolDto.getId())
        );
    }

    @Override
    public ResponseMeasuringToolDto get(Long id) {
        return mapper.mapToResponseMeasuringToolDto(
                            repository.findById(id)
                                    .orElseThrow(() -> new NotFoundException(
                                            String.format("measuring tool with id = %s not found", id))));
    }

    @Override
    public List<ResponseMeasuringToolDto> getAll(SearchParameters parameters) {
        QMeasuringTool measuringTool = QMeasuringTool.measuringTool;
        List<MeasuringTool> measuringTools = new JPAQueryFactory(em).from(measuringTool)
                .select(measuringTool)
                .where(getPredicate(measuringTool, parameters))
                .fetch();
        if (measuringTools.isEmpty()) {
            measuringTools = repository.findAll();
        }
        return measuringTools.stream()
                             .map(mapper::mapToResponseMeasuringToolDto)
                             .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("measuring tool with id = %s not found for delete", id));
    }

    @Override
    public List<MeasuringTool> getAllLaboratoryEmployeeId(List<Long>  employeesIds) {
        QMeasuringTool measuringTool = QMeasuringTool.measuringTool;
        return new JPAQueryFactory(em).from(measuringTool)
                .select(measuringTool)
                .where(measuringTool.laboratoryEmployeeId.in(employeesIds))
                .fetch();
    }

    private MeasuringTool getByPredicate(MeasuringToolDto measuringToolDto) {
        QMeasuringTool measuringTool = QMeasuringTool.measuringTool;
        return new JPAQueryFactory(em).from(measuringTool)
                .select(measuringTool)
                .where(getPredicate(measuringTool, mapper.mapToRequestParameters(measuringToolDto)))
                .fetchOne();
    }

    private BooleanBuilder getPredicate(QMeasuringTool measuringTool, SearchParameters parameters) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (parameters.getIds() != null && !parameters.getIds().isEmpty()) {
            booleanBuilder.and(measuringTool.laboratoryEmployeeId.in(parameters.getIds()));
        }
        if (parameters.getToll() != null && !parameters.getToll().isEmpty()) {
            booleanBuilder.and(measuringTool.toll.in(parameters.getToll()));
        }
        if (parameters.getModel() != null && !parameters.getModel().isEmpty()) {
            booleanBuilder.and(measuringTool.model.in(parameters.getModel()));
        }
        if (parameters.getWorkNumber() != null) {
            booleanBuilder.and(measuringTool.workNumber.eq(parameters.getWorkNumber()));
        }
        if (parameters.getManufacturing() != null) {
            booleanBuilder.and(measuringTool.manufacturing.eq(parameters.getManufacturing()));
        }
        if (parameters.getExploitation() != null) {
            booleanBuilder.and(measuringTool.exploitation.eq(parameters.getExploitation()));
        }
        if (parameters.getManufacturer() != null) {
            booleanBuilder.and(measuringTool.manufacturer.eq(parameters.getManufacturer()));
        }
       return booleanBuilder;
    }
}