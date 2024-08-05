package ru.nabokovsg.diagnosedEquipment.service.diagnosedEquipment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentPassport.EquipmentPassportDto;
import ru.nabokovsg.diagnosedEquipment.dto.diagnosedEquipment.equipmentPassport.ResponseEquipmentPassportDto;
import ru.nabokovsg.diagnosedEquipment.exceptions.NotFoundException;
import ru.nabokovsg.diagnosedEquipment.mapper.diagnosedEquipment.EquipmentPassportMapper;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.EquipmentPassport;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.QEquipmentDiagnosed;
import ru.nabokovsg.diagnosedEquipment.model.diagnosedEquipment.QEquipmentPassport;
import ru.nabokovsg.diagnosedEquipment.repository.diagnosedEquipment.EquipmentPassportRepository;

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