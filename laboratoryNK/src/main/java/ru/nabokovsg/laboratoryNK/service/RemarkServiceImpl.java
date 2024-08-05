package ru.nabokovsg.laboratoryNK.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.laboratoryNK.dto.remark.RemarkDto;
import ru.nabokovsg.laboratoryNK.dto.remark.ResponseRemarkDto;
import ru.nabokovsg.laboratoryNK.exceptions.BadRequestException;
import ru.nabokovsg.laboratoryNK.exceptions.NotFoundException;
import ru.nabokovsg.laboratoryNK.mapper.RemarkMapper;
import ru.nabokovsg.laboratoryNK.model.*;
import ru.nabokovsg.laboratoryNK.repository.RemarkRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RemarkServiceImpl implements RemarkService {

    private final RemarkRepository repository;
    private final RemarkMapper mapper;
    private final LaboratoryEmployeeService employeeService;
    private final DiagnosticDocumentService documentService;
    private final EntityManager em;

    @Override
    public ResponseRemarkDto save(RemarkDto remarkDto) {
        Remark remark = repository.findByDocumentIdAndRemark(remarkDto.getDocumentId(), remarkDto.getRemark());
        if (remark == null) {
            remark = repository.save(mapper.mapToRemark(remarkDto
                            , employeeService.getById(remarkDto.getEmployeeId())
                            , documentService.getById(remarkDto.getDocumentId())));
            documentService.updateStatus(remark.getDocument(), DocumentStatus.REMARK);
            return mapper.mapToRemarkDto(remark);
        }
       throw new BadRequestException(String.format("Remark was found: %s", remark.getRemark()));
    }

    @Override
    public ResponseRemarkDto update(RemarkDto remarkDto) {
        Remark remark = getById(remarkDto.getId());
        remark = repository.save(mapper.mapToRemark(remarkDto
                               , employeeService.getById(remarkDto.getEmployeeId())
                               , remark.getDocument()));
        return mapper.mapToRemarkDto(remark);
    }

    @Override
    public List<ResponseRemarkDto> getAll(Long id) {
        Set<Remark> remarks = repository.findAllByEmployeeId(id);
        if (remarks.isEmpty()) {
            QRemark remark = QRemark.remark1;
            QWorkJournal journal = QWorkJournal.workJournal;
            QDiagnosticDocument document = QDiagnosticDocument.diagnosticDocument;
            QLaboratoryEmployee employee = QLaboratoryEmployee.laboratoryEmployee;
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(document.journal.id.eq(journal.id));
            booleanBuilder.and(remark.document.id.eq(document.id));
            booleanBuilder.and(employee.id.eq(id));
            return new JPAQueryFactory(em).from(remark)
                                          .select(remark)
                                          .where(booleanBuilder)
                                          .fetch()
                                          .stream()
                                          .map(mapper::mapToRemarkDto)
                                          .toList();
        }
        return remarks.stream()
                .map(mapper::mapToRemarkDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        Remark remark = getById(id);
        repository.deleteById(id);
        if (repository.countAllByDocumentId(remark.getDocument().getId()) == 0) {
            documentService.updateStatus(remark.getDocument(), DocumentStatus.ACCEPTED);
        }
    }

    private Remark getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Remark with id=%s not found", id)));
    }
}