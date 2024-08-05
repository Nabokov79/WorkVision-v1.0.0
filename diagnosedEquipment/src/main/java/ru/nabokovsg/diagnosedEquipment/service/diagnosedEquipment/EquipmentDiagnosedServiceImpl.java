package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed.EquipmentDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed.ResponseEquipmentDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentDiagnosed.ResponseShortEquipmentDto;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentDiagnosed;
import ru.nabokovsg.diagnosedEquipment.exceptions.BadRequestException;
import ru.nabokovsg.diagnosedEquipment.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment.EquipmentDiagnosedMapper;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.QEquipmentDiagnosed;
import ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment.EquipmentDiagnosedRepository;
import ru.nabokovsg.diagnosedEquipment.service.equipmentType.EquipmentTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentDiagnosedServiceImpl implements EquipmentDiagnosedService {

    private final EquipmentDiagnosedRepository repository;
    private final EquipmentDiagnosedMapper mapper;
    private final EquipmentTypeService equipmentTypeService;
    private final EntityManager em;

    @Override
    public ResponseEquipmentDto save(EquipmentDto equipmentDto) {
        if (getByPredicate(equipmentDto) == null) {
            return mapper.mapToResponseEquipmentDto(
                    repository.save(mapper.mapToEquipment(equipmentDto
                            , equipmentTypeService.getById(equipmentDto.getEquipmentTypeId())))
            );
        }
        throw new BadRequestException(
                String.format("EquipmentDiagnosed by equipmentTypeId=%s and buildingId=%s and room=%s is found"
                , equipmentDto.getEquipmentTypeId(), equipmentDto.getBuildingId(), equipmentDto.getRoom()));
    }

    @Override
    public ResponseEquipmentDto update(EquipmentDto equipmentDto) {
       if (repository.existsById(equipmentDto.getId())) {
           return mapper.mapToResponseEquipmentDto(
                   repository.save(mapper.mapToEquipment(equipmentDto
                           , equipmentTypeService.getById(equipmentDto.getEquipmentTypeId())))
           );
       }
        throw new NotFoundException(String.format("Equipment with id=%s not found for update", equipmentDto.getId()));
    }

    @Override
    public ResponseEquipmentDto get(Long id) {
        return mapper.mapToResponseEquipmentDto(getById(id));
    }

    @Override
    public List<ResponseShortEquipmentDto> getAll(Long id) {
        return repository.findAllByBuildingId(id).stream()
                .map(mapper::mapToResponseShortEquipmentDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Equipment with id=%s not found for delete", id));
    }

    @Override
    public EquipmentDiagnosed getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Equipment with id=%s not found", id)));
    }

    private EquipmentDiagnosed getByPredicate(EquipmentDto equipmentDto) {
        QEquipmentDiagnosed equipment = QEquipmentDiagnosed.equipmentDiagnosed;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(equipment.equipmentTypeId.eq(equipmentDto.getEquipmentTypeId()));
        builder.and(equipment.buildingId.eq(equipmentDto.getBuildingId()));
        if (equipmentDto.getRoom() != null) {
            builder.and(equipment.room.eq(equipmentDto.getRoom()));
        }
        return new JPAQueryFactory(em)
                        .from(equipment)
                        .select(equipment)
                        .where(builder)
                        .fetchOne();
    }
}