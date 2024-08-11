package ru.nabokovsg.diagnosedNK.service.equipment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPassport.EquipmentPassportDto;
import ru.nabokovsg.diagnosedNK.dto.equipment.equipmentPassport.ResponseEquipmentPassportDto;
import ru.nabokovsg.diagnosedNK.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedNK.mapper.equipment.EquipmentPassportMapper;
import ru.nabokovsg.diagnosedNK.model.equipment.EquipmentPassport;
import ru.nabokovsg.diagnosedNK.model.equipment.QEquipmentDiagnosed;
import ru.nabokovsg.diagnosedNK.model.equipment.QEquipmentPassport;
import ru.nabokovsg.diagnosedNK.repository.equipment.EquipmentPassportRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EquipmentPassportServiceImpl implements EquipmentPassportService {

    private final EquipmentPassportRepository repository;
    private final EquipmentPassportMapper mapper;
    private final EquipmentDiagnosedService equipmentService;
    private final EntityManager em;

    @Override
    public ResponseEquipmentPassportDto save(EquipmentPassportDto passportDto) {
        return mapper.mapToResponseEquipmentPassportDto(
                Objects.requireNonNullElseGet(
                        getByHeaderAndEquipmentId(passportDto.getEquipmentId(), passportDto.getHeader())
                        , () -> repository.save(mapper.mapToEquipmentPassport(passportDto
                                , equipmentService.getById(passportDto.getEquipmentId()))))
        );
    }

    @Override
    public ResponseEquipmentPassportDto update(EquipmentPassportDto passportDto) {
        if (repository.existsById(passportDto.getId())) {
            return mapper.mapToResponseEquipmentPassportDto(repository.save(mapper.mapToEquipmentPassport(passportDto
                    , equipmentService.getById(passportDto.getEquipmentId()))));
        }
        throw new NotFoundException(
                String.format("Equipment passport with id=%s not found for update", passportDto.getId())
        );
    }

    @Override
    public List<ResponseEquipmentPassportDto> getAll(Long id) {
        return repository.findAllByEquipmentDiagnosedId(id)
                         .stream()
                         .map(mapper::mapToResponseEquipmentPassportDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Equipment Passport with id=%s not found for delete", id));
    }

    private EquipmentPassport getByHeaderAndEquipmentId(Long equipmentId, String header) {
        QEquipmentPassport passport = QEquipmentPassport.equipmentPassport;
        QEquipmentDiagnosed equipment = QEquipmentDiagnosed.equipmentDiagnosed;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(equipment.id.eq(equipmentId));
        booleanBuilder.and(passport.header.eq(header));
        return new JPAQueryFactory(em).from(passport)
                .select(passport)
                .where(booleanBuilder)
                .fetchOne();
    }
}