package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentRepair.EquipmentRepairDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentRepair.ResponseEquipmentRepairDto;
import ru.nabokovsg.diagnosedEquipment.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment.EquipmentRepairMapper;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentRepair;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.QEquipmentRepair;
import ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment.EquipmentRepairRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EquipmentRepairServiceImpl implements EquipmentRepairService {

    private final EquipmentRepairRepository repository;
    private final EquipmentRepairMapper mapper;
    private final EntityManager em;

    @Override
    public ResponseEquipmentRepairDto save(EquipmentRepairDto repairDto) {
        return mapper.mapToResponseEquipmentRepairDto(
                Objects.requireNonNullElseGet(getByPredicate(repairDto)
                                            , () -> repository.save(mapper.mapToEquipmentRepair(repairDto))));
    }

    @Override
    public ResponseEquipmentRepairDto update(EquipmentRepairDto repairDto) {
        if (repository.existsById(repairDto.getId())) {
            return mapper.mapToResponseEquipmentRepairDto(
                    repository.save(mapper.mapToEquipmentRepair(repairDto)));
        }
        throw new NotFoundException(
                String.format("Equipment repair with id=%s not found for update", repairDto.getId()));
    }

    @Override
    public List<ResponseEquipmentRepairDto> getAll(Long id) {
        return repository.findAllByEquipmentDiagnosedId(id)
                .stream()
                .map(mapper::mapToResponseEquipmentRepairDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Equipment repair with id=%s not found for delete", id));
    }

    private EquipmentRepair getByPredicate(EquipmentRepairDto repairDto) {
        QEquipmentRepair repair = QEquipmentRepair.equipmentRepair;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(repair.date.eq(repairDto.getDate()));
        booleanBuilder.and(repair.description.eq(repairDto.getDescription()));
        booleanBuilder.and(repair.organization.eq(repairDto.getOrganization()));
        booleanBuilder.and(repair.equipmentDiagnosedId.eq(repairDto.getEquipmentDiagnosedId()));
        return new JPAQueryFactory(em).from(repair)
                                      .select(repair)
                                      .where(booleanBuilder)
                                      .fetchOne();
    }
}
