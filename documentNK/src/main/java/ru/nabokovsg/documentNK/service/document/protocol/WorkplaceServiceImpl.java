package ru.nabokovsg.documentNK.service.document.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.documentNK.dto.client.documentCreate.DocumentCreationDataDto;
import ru.nabokovsg.documentNK.mapper.document.protocol.WorkplaceMapper;
import ru.nabokovsg.documentNK.model.document.protocol.Workplace;
import ru.nabokovsg.documentNK.repository.document.protocol.WorkplaceRepository;

@Service
@RequiredArgsConstructor
public class WorkplaceServiceImpl implements WorkplaceService {

    private final WorkplaceRepository repository;
    private final WorkplaceMapper mapper;

    @Override
    public Workplace save(DocumentCreationDataDto journal) {
        return repository.save(mapper.mapToWorkplace(journal));
    }
}